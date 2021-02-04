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
<<<<<<< HEAD
    }//

    Boolean validarRegistro() {
        if (correo.getText().toString().trim().length() == 0) {
            Toast.makeText(this, getString(R.string.msg_registro_mail), Toast.LENGTH_LONG).show();
            return false;
        }

        if (!General.validarMail(correo.getText().toString())) {
            Toast.makeText(this, getString(R.string.msg_registro_valMail), Toast.LENGTH_LONG).show();
            return false;
        }

        if (password.getText().toString().trim().length() == 0) {
            Toast.makeText(this, getString(R.string.msg_registro_password), Toast.LENGTH_LONG).show();
            return false;
        }
        if (repassword.getText().toString().trim().length() == 0) {
            Toast.makeText(this, getString(R.string.msg_registro_repasswod), Toast.LENGTH_LONG).show();
            return false;
        }

        if (!password.getText().toString().equals(repassword.getText().toString())) {
            Toast.makeText(this, getString(R.string.msg_registro_valPass), Toast.LENGTH_LONG).show();
            return false;
        }

        if (nombre.getText().toString().trim().length() == 0) {
            Toast.makeText(this, getString(R.string.msg_registro_nombre), Toast.LENGTH_LONG).show();
            return false;
        }
        if (apellido.getText().toString().trim().length() == 0) {
            Toast.makeText(this, getString(R.string.msg_registro_apellido), Toast.LENGTH_LONG).show();
            return false;
        }
        if (telefono.getText().toString().trim().length() == 0) {
            Toast.makeText(this, getString(R.string.msg_registro_telefono), Toast.LENGTH_LONG).show();
            return false;
        }
        if (telefono.getText().toString().trim().length() != 9) {
            Toast.makeText(this, getString(R.string.msg_registro_format_telefono), Toast.LENGTH_LONG).show();
            return false;
        }


        return true;
    }

    void crearUsuario() {

        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(VeterinariaContract.UsuarioEntry.COLUMN_NAME_CORREO, correo.getText().toString().trim());
            values.put(VeterinariaContract.UsuarioEntry.COLUMN_NAME_PASSWORD, password.getText().toString().trim());
            values.put(VeterinariaContract.UsuarioEntry.COLUMN_NAME_TIPO_USUARIO, "1");
            values.put(VeterinariaContract.UsuarioEntry.COLUMN_NAME_NOMBRE, nombre.getText().toString().trim());
            values.put(VeterinariaContract.UsuarioEntry.COLUMN_NAME_PATERNO, apellido.getText().toString().trim());
            values.put(VeterinariaContract.UsuarioEntry.COLUMN_NAME_CELULAR, telefono.getText().toString().trim());
            values.put(VeterinariaContract.UsuarioEntry.COLUMN_NAME_ESTADO, "1");
// Insert the new row, returning the primary key value of the new row
            newRowId = db.insert(VeterinariaContract.UsuarioEntry.TABLE_NAME, null, values);
        }
        catch (Exception ex){
            System.out.println("Error de registro SQLLite ");
            System.out.println("Mensaje: "+ ex.getMessage());
            Toast.makeText(this, getString(R.string.msg_error_ocurrio),Toast.LENGTH_LONG).show();
        }
=======
>>>>>>> parent of 19fcadd... observaciones + validacion ( login + registrarme) + Configuracion SQLLite
    }
}