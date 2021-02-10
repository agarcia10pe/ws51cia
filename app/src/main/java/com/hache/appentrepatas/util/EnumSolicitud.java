package com.hache.appentrepatas.util;

public class EnumSolicitud {
    public enum TipoUsuario {
        ADMINISTRADOR (1),
        CLIENTE (2);

        private int codigo;
        private TipoUsuario(int codigo) {
            this.codigo = codigo;
        }

        public int getCode() {
            return this.codigo;
        }
    }
}
