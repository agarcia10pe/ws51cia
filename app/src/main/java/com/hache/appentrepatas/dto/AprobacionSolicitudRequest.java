package com.hache.appentrepatas.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AprobacionSolicitudRequest {
    @SerializedName("idSolicitud")
    @Expose
    private int idSolicitud;

    @SerializedName("correo2")
    @Expose
    private String correo2;

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getCorreo2() {
        return correo2;
    }

    public void setCorreo2(String correo2) {
        this.correo2 = correo2;
    }
}
