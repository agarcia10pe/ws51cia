package com.hache.appentrepatas;


import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import static android.content.ContentValues.TAG;

public class FbMessagingService extends FirebaseMessagingService {

    public FbMessagingService(){
        System.out.println("inicio servicio");
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

    }

}
