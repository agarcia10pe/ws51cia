package com.hache.appentrepatas.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfirmacionSolicitudRequest {
    @SerializedName("idSolicitud")
    @Expose
    private int idSolicitud;

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
}
