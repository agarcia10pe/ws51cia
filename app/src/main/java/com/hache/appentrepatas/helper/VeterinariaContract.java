package com.hache.appentrepatas.helper;

import android.provider.BaseColumns;

public class VeterinariaContract {

    private VeterinariaContract() {}

    public static class UsuarioEntry implements BaseColumns {
        public static final String TABLE_NAME = "TB_usuario";
        public static final String COLUMN_NAME_CORREO = "correo";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_TIPO_USUARIO = "idtipousu";
        public static final String COLUMN_NAME_NOMBRE = "nombre";
        public static final String COLUMN_NAME_PATERNO = "apepaterno";
        public static final String COLUMN_NAME_CELULAR = "celular";
        public static final String COLUMN_NAME_ESTADO = "estusuario";
    }
}
