package com.hache.appentrepatas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hache.appentrepatas.dto.BaseResponse;
import com.hache.appentrepatas.dto.ClienteRequest;
import com.hache.appentrepatas.helper.SharedPreferencesManager;
import com.hache.appentrepatas.http.SolicitudClient;
import com.hache.appentrepatas.http.SolicitudService;
import com.hache.appentrepatas.util.Constants;
import com.hache.appentrepatas.util.EnumSolicitud;
import com.hache.appentrepatas.util.General;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingupActivity extends AppCompatActivity implements View.OnClickListener {

    Button register_btn;
    Intent intent;
    EditText correo, password, repassword, nombre, apellido, telefono;
    ProgressDialog progressDialog;
    SolicitudService solicitudService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        initView();
        initRetrofit();
        initProgressDialog();
    }

    private void initView() {
        correo = (EditText)  findViewById(R.id.et_singup_correo);
        password = (EditText)  findViewById(R.id.et_singup_password);
        repassword = (EditText)  findViewById(R.id.et_singup_repassword);
        nombre = (EditText)  findViewById(R.id.et_singup_nombre);
        apellido = (EditText)  findViewById(R.id.et_singup_paterno);
        telefono = (EditText)  findViewById(R.id.et_singup_telefono);
        register_btn = (Button) findViewById(R.id.btn_singup_register);
        register_btn.setOnClickListener(this);
    }

    private void initRetrofit() {
        solicitudService = SolicitudClient.getInstance().getSolicitudService();
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getString(R.string.mensaje_procesando));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_singup_register:
                if (validarRegistro())
                    registrarCliente();
                break;
            default:
                break;
        }
    }

    private boolean validarRegistro() {
        if (correo.getText().toString().trim().length() == 0) {
            Toast.makeText(this, getString(R.string.msg_singup_mail), Toast.LENGTH_LONG).show();
            return false;
        }

        if (!General.validarMail(correo.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.msg_singup_valMail), Toast.LENGTH_LONG).show();
            return false;
        }

        if (password.getText().toString().trim().length() == 0) {
            Toast.makeText(this, getString(R.string.msg_singup_password), Toast.LENGTH_LONG).show();
            return false;
        }
        if (repassword.getText().toString().trim().length() == 0) {
            Toast.makeText(this, getString(R.string.msg_singup_repasswod), Toast.LENGTH_LONG).show();
            return false;
        }

        if (!password.getText().toString().equals(repassword.getText().toString())) {
            Toast.makeText(this, getString(R.string.msg_singup_valPass), Toast.LENGTH_LONG).show();
            return false;
        }

        if (nombre.getText().toString().trim().length() == 0) {
            Toast.makeText(this, getString(R.string.msg_singup_nombre), Toast.LENGTH_LONG).show();
            return false;
        }
        if (apellido.getText().toString().trim().length() == 0) {
            Toast.makeText(this, getString(R.string.msg_singup_apellido), Toast.LENGTH_LONG).show();
            return false;
        }
        if (telefono.getText().toString().trim().length() == 0) {
            Toast.makeText(this, getString(R.string.msg_singup_telefono), Toast.LENGTH_LONG).show();
            return false;
        }
        if (telefono.getText().toString().trim().length() != 9) {
            Toast.makeText(this, getString(R.string.msg_singup_format_telefono), Toast.LENGTH_LONG).show();
            return false;
        }


        return true;
    }

    void registrarCliente() {
        progressDialog.show();
        ClienteRequest request = new ClienteRequest();
        request.setCorreo(correo.getText().toString().trim());
        request.setContrasena(password.getText().toString().trim());
        request.setIdTipoUsu((byte)EnumSolicitud.TipoUsuario.CLIENTE.getCode());
        request.setNombre(nombre.getText().toString().trim());
        request.setApePaterno(apellido.getText().toString().trim());
        request.setCelular(Integer.parseInt(telefono.getText().toString()));
        request.setToken(SharedPreferencesManager.getSomeStringValue(Constants.PREF_TOKEN_FB));

        Call<BaseResponse<String>> call = solicitudService.registrarCliente(request);

        call.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                progressDialog.dismiss();
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.msg_error_ocurrio), Toast.LENGTH_LONG).show();
                    return;
                }

                if (response.body().getCodigo() == 0) {
                    String mensaje = response.body().getData() != null ? response.body().getData() : getString(R.string.msg_error_ocurrio);
                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(getApplicationContext(), getString(R.string.msg_singup_exito), Toast.LENGTH_LONG).show();
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), R.string.mensaje_error_conexion, Toast.LENGTH_SHORT).show();
            }
        });
    }
}