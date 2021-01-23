package com.hache.appentrepatas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView register_txt;
    Button login_btn;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register_txt = (TextView) findViewById(R.id.lbl_login_register);
        login_btn = (Button) findViewById(R.id.btn_login);

        login_btn.setOnClickListener(this);
        register_txt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lbl_login_register:
                intent = new Intent(this, SingupActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}