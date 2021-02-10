package com.hache.appentrepatas.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PerroPartialDTO {
    @SerializedName("idPerro")
    @Expose
    private short idPerro;

    @SerializedName("nombrePerro")
    @Expose
    private String nombrePerro;

    @SerializedName("imgPerro")
    @Expose
    private String ImgPerro;

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
        return ImgPerro;
    }

    public void setImgPerro(String imgPerro) {
        ImgPerro = imgPerro;
    }
}
