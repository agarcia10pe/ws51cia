package com.hache.appentrepatas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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