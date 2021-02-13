package com.hache.appentrepatas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hache.appentrepatas.dto.BaseResponse;
import com.hache.appentrepatas.dto.LoginRequest;
import com.hache.appentrepatas.dto.UsuarioDTO;
import com.hache.appentrepatas.helper.SharedPreferencesManager;
import com.hache.appentrepatas.http.SolicitudClient;
import com.hache.appentrepatas.http.SolicitudService;
import com.hache.appentrepatas.util.Constants;
import com.hache.appentrepatas.util.General;
import com.hache.appentrepatas.util.SeguridadUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView register_txt;
    Button login_btn;
    EditText user_txt, pass_txt;

    Intent intent;
    SolicitudService solicitudService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SeguridadUtil.esAutenticado()) {
            Constants.user = SharedPreferencesManager.getSomeStringValue(Constants.PREF_NOMBRE) + " " + SharedPreferencesManager.getSomeStringValue(Constants.PREF_APELLIDO);
            intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            return;
        }

        setContentView(R.layout.activity_login);

        register_txt = (TextView) findViewById(R.id.lbl_login_register);
        login_btn = (Button) findViewById(R.id.btn_login);
        user_txt = (EditText)  findViewById(R.id.user_LoginTxt);
        pass_txt = (EditText)  findViewById(R.id.pass_LoginTxt);
        login_btn.setOnClickListener(this);
        register_txt.setOnClickListener(this);

        solicitudService = SolicitudClient.getInstance().getSolicitudService();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lbl_login_register:
                intent = new Intent(this, SingupActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                if(valLogin()){

                    LoginRequest request = new LoginRequest();
                    request.setCorreo(user_txt.getText().toString());
                    request.setContrasena(pass_txt.getText().toString().trim());

                    Call<BaseResponse<UsuarioDTO>> call = solicitudService.autenticarUsuario(request);

                    call.enqueue(new Callback<BaseResponse<UsuarioDTO>>() {
                        @Override
                        public void onResponse(Call<BaseResponse<UsuarioDTO>> call, Response<BaseResponse<UsuarioDTO>> response) {

                            if (response.body().getCodigo() == 1) {
                                Toast.makeText(getApplicationContext() , getString(R.string.msg_login_exito), Toast.LENGTH_LONG).show();
                                Constants.user = response.body().getData().getNombre() + " " + response.body().getData().getApePaterno();
                                SeguridadUtil.asignarUsuario(response.body().getData());
                                intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                return;
                            }

                            Toast.makeText(getApplicationContext() , getString(R.string.msg_login_incorecto), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<BaseResponse<UsuarioDTO>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }

                break;
            default:
                break;
        }
    }

    boolean valLogin(){
        if(user_txt.getText().toString().trim().length()==0){
            Toast.makeText(this, getString(R.string.msg_login_usuario), Toast.LENGTH_LONG).show();
            return false;
        }

        if (!General.validarMail(user_txt.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.msg_singup_valMail), Toast.LENGTH_LONG).show();
            return false;
        }

        if(pass_txt.getText().toString().trim().length()==0){
            Toast.makeText(this, getString(R.string.msg_login_password), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}