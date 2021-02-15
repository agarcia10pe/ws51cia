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
//import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hache.appentrepatas.helper.SharedPreferencesManager;
import com.hache.appentrepatas.util.Constants;
import com.hache.appentrepatas.util.SeguridadUtil;

import static android.content.ContentValues.TAG;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    TextView login_txt, singup_txt;
//aqui
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFirebaseMessaging();
        if (SeguridadUtil.esAutenticado()) {
            redireccionarHome();
            return;
        }

        setContentView(R.layout.activity_home);
        initView();
    }

    private void initFirebaseMessaging() {
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        String token = task.getResult();
                        Log.d(TAG, "TOKEN firebase: "+ token);
                        SharedPreferencesManager.setSomeStringValue(Constants.PREF_TOKEN_FB, token);
                    }
        });
    }

    private void redireccionarHome() {
        Constants.user = SharedPreferencesManager.getSomeStringValue(Constants.PREF_NOMBRE) + " " + SharedPreferencesManager.getSomeStringValue(Constants.PREF_APELLIDO);
        intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void initView() {
        login_txt =(TextView) findViewById(R.id.lbl_login);
        singup_txt = (TextView) findViewById(R.id.lbl_singup);

        login_txt.setOnClickListener(this);
        singup_txt.setOnClickListener(this);
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