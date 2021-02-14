package com.hache.appentrepatas.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TerminoSolicitudRequest {
    @SerializedName("idSolicitud")
    @Expose
    private int idSolicitud;

    @SerializedName("imgSolicitud")
    @Expose
    private String imgSolicitud;

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getImgSolicitud() {
        return imgSolicitud;
    }

    public void setImgSolicitud(String imgSolicitud) {
        this.imgSolicitud = imgSolicitud;
    }
}
