package com.hache.appentrepatas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SingupActivity extends AppCompatActivity implements View.OnClickListener {

    Button register_btn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        register_btn = (Button) findViewById(R.id.btn_singup_register);
        register_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_singup_register:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            default:
                break;
        }
    }
}