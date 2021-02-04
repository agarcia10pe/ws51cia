package com.hache.appentrepatas.helper;

import android.provider.BaseColumns;

public final class VeterinariaContract {

    public VeterinariaContract(){ }

    public static class UsuarioEntry implements BaseColumns{
        public static final String TABLE_NAME = "usuario";
        public static final String COLUMN_NAME_CORREO = "correo";
        public static final String COLUMN_NAME_PASSWORD = "passwors";
        public static final String COLUMN_NAME_TIPO_USUARIO = "idTipoUsu";
        public static final String COLUMN_NAME_NOMBRE = "Nombre";
        public static final String COLUMN_NAME_PATERNO = "apePaterno";
        public static final String COLUMN_NAME_CELULAR = "celular";
        public static final String COLUMN_NAME_ESTADO = "estUsuario";

    }


}
