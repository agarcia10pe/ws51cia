package com.hache.appentrepatas.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SolicitarAdopcionRequest {
    @SerializedName("correo")
    @Expose
    private String correo;

    @SerializedName("idPerro")
    @Expose
    private short idPerro;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public short getIdPerro() {
        return idPerro;
    }

    public void setIdPerro(short idPerro) {
        this.idPerro = idPerro;
    }
}
