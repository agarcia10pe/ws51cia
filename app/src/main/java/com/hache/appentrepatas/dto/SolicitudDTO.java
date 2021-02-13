package com.hache.appentrepatas.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SolicitudDTO {
    @SerializedName("idSolicitud")
    @Expose
    private int idSolicitud;

    @SerializedName("idEstSolicitud")
    @Expose
    private int idEstSolicitud;

    @SerializedName("descripcionEstado")
    @Expose
    private String descripcionEstado;

    @SerializedName("correo")
    @Expose
    private String correo;

    @SerializedName("nombre1")
    @Expose
    private String nombre1;

    @SerializedName("apePaterno")
    @Expose
    private String apePaterno;

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

    public void setIdEstSolicitud(int idEstSolicitud) {
        this.idEstSolicitud = idEstSolicitud;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getApePaterno() {
        return apePaterno;
    }

    public void setApePaterno(String apePaterno) {
        this.apePaterno = apePaterno;
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
