package com.hache.appentrepatas.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsuarioDTO {
    @SerializedName("IdTipoUsu")
    @Expose
    private byte idTipoUsu;

    @SerializedName("Nombre1")
    @Expose
    private String nombre;

    @SerializedName("ApePaterno")
    @Expose
    private String apePaterno;

    @SerializedName("correo")
    @Expose
    private String correo;

    public byte getIdTipoUsu() {
        return idTipoUsu;
    }

    public void setIdTipoUsu(byte idTipoUsu) {
        this.idTipoUsu = idTipoUsu;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApePaterno() {
        return apePaterno;
    }

    public void setApePaterno(String apePaterno) {
        this.apePaterno = apePaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
