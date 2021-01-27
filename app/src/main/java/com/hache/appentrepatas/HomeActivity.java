package com.hache.appentrepatas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import static android.content.ContentValues.TAG;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    TextView login_txt, singup_txt;
//aqui
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        login_txt =(TextView) findViewById(R.id.lbl_login);
        singup_txt = (TextView) findViewById(R.id.lbl_singup);

        login_txt.setOnClickListener(this);
        singup_txt.setOnClickListener(this);

        System.out.println("loading");
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        //String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, "TOKEN firebase: "+token);
                        //Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lbl_login:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.lbl_singup:
                intent = new Intent(this, SingupActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}