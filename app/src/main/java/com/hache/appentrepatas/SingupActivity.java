package com.hache.appentrepatas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hache.appentrepatas.helper.VeterinariaContract;
import com.hache.appentrepatas.helper.VeterinariaDbHelper;
import com.hache.appentrepatas.util.General;

public class SingupActivity extends AppCompatActivity implements View.OnClickListener {

    Button register_btn;
    Intent intent;

    EditText correo, password, repassword, nombre, apellido, telefono;
    VeterinariaDbHelper dbHelper;

    long newRowId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        correo = (EditText)  findViewById(R.id.et_singup_correo);
        password = (EditText)  findViewById(R.id.et_singup_password);
        repassword = (EditText)  findViewById(R.id.et_singup_repassword);
        nombre = (EditText)  findViewById(R.id.et_singup_nombre);
        apellido = (EditText)  findViewById(R.id.et_singup_paterno);
        telefono = (EditText)  findViewById(R.id.et_singup_telefono);
        register_btn = (Button) findViewById(R.id.btn_singup_register);
        register_btn.setOnClickListener(this);

        dbHelper = new  VeterinariaDbHelper(this);
        //FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(getContext());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_singup_register:
                if(validarRegistro()){
                    crearUsuario();
                    if(newRowId> 0){
                        Toast.makeText(this, getString(R.string.msg_singup_exito), Toast.LENGTH_LONG).show();
                        intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                    }
                }

            default:
                break;
        }
    }//

    Boolean validarRegistro() {
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

    }
}