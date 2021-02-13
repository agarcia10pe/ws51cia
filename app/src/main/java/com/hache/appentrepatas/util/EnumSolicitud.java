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

    public enum EstadoSolicitud {
        INICIADO (1),
        PENDIENTE (2),
        APROBADO (3),
        RECHAZADO (4),
        FINALIZADO (5);

        private int codigo;
        private EstadoSolicitud(int codigo) {
            this.codigo = codigo;
        }

        public int getCode() {
            return this.codigo;
        }
    }
}
