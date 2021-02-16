package com.hache.appentrepatas.util;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hache.appentrepatas.MainActivity;
import com.hache.appentrepatas.R;
import com.hache.appentrepatas.helper.DbContract;
import com.hache.appentrepatas.helper.EntrePatasDbHelper;
import com.hache.appentrepatas.helper.MyApp;
import com.hache.appentrepatas.helper.SharedPreferencesManager;
import com.hache.appentrepatas.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class FbMessagingService extends FirebaseMessagingService {
    SQLiteDatabase db;

    public FbMessagingService(){
        System.out.println("inicio servicio");
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        EntrePatasDbHelper dbHelper = new EntrePatasDbHelper(MyApp.getContext());
        db = dbHelper.getReadableDatabase();
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed token: " + token);
        SharedPreferencesManager.setSomeStringValue(Constants.PREF_TOKEN_FB, token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "Mensaje Recibido: " + remoteMessage.getData().toString());

        if (!isActiveNotification()) return;

        Map<String, String> data = remoteMessage.getData();
        if (data.size() > 0) {
            Log.d(TAG, "data: " + data);
            String title = data.get("titulo");
            String msg = data.get("mensaje");
            sendNotification(msg);

        } else {
            RemoteMessage.Notification notification = remoteMessage.getNotification();
            String title = notification.getTitle();
            String msg = notification.getBody();

            sendNotification(msg);
        }
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_logo)
                        .setContentTitle(getString(R.string.fcm_message))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private boolean isActiveNotification() {
        String[] projection = {
                BaseColumns._ID,
                DbContract.ConfigurationEntry.COLUMN_USUARIO,
                DbContract.ConfigurationEntry.COLUMN_ACTIVO
        };

        String sortOrder = DbContract.ConfigurationEntry.COLUMNS_FECHA + " DESC";
        Cursor cursor = db.query( DbContract.ConfigurationEntry.TABLE_NAME, projection, null, null, null, null, sortOrder);

        List items = new ArrayList();
        while(cursor.moveToNext()) {
            String activo  = cursor.getString(
                    cursor.getColumnIndexOrThrow(DbContract.ConfigurationEntry.COLUMN_ACTIVO));
            items.add(activo);
        }
        cursor.close();

        if (items.size() == 0)
            return  false;

        return  (items.get(0).equals("1"));
    }
}
