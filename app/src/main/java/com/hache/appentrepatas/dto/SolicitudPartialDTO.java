package com.hache.appentrepatas.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SolicitudPartialDTO {
    @SerializedName("idSolicitud")
    @Expose
    private int idSolicitud;

    @SerializedName("idEstSolicitud")
    @Expose
    private int idEstSolicitud;

    @SerializedName("descripcionEstado")
    @Expose
    private String descripcionEstado;

    @SerializedName("idPerro")
    @Expose
    private short idPerro;

    @SerializedName("nombrePerro")
    @Expose
    private String nombrePerro;

    @SerializedName("imgPerro")
    @Expose
    private String imgPerro;

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public int getIdEstSolicitud() {
        return idEstSolicitud;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public void setIdEstSolicitud(int idEstSolicitud) {
        this.idEstSolicitud = idEstSolicitud;
    }

    public short getIdPerro() {
        return idPerro;
    }

    public void setIdPerro(short idPerro) {
        this.idPerro = idPerro;
    }

    public String getNombrePerro() {
        return nombrePerro;
    }

    public void setNombrePerro(String nombrePerro) {
        this.nombrePerro = nombrePerro;
    }

    public String getImgPerro() {
        return imgPerro;
    }

    public void setImgPerro(String imgPerro) {
        this.imgPerro = imgPerro;
    }
}
