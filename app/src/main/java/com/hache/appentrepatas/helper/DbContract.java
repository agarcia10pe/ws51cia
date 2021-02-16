package com.hache.appentrepatas.helper;

import android.provider.BaseColumns;

public class DbContract {
    private DbContract() {}
    public static class ConfigurationEntry   implements BaseColumns {
        public static final String TABLE_NAME = "TB_configuracion";
        public static final String COLUMN_USUARIO = "usuario";
        public static final String COLUMN_ACTIVO = "activo";
        public static final String COLUMNS_FECHA = "fec_registro";
    }
}
