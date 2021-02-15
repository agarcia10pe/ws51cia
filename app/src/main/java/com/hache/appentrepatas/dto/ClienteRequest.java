package com.hache.appentrepatas.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClienteRequest {
    @SerializedName("Correo")
    @Expose
    private String correo;

    @SerializedName("Contraseña")
    @Expose
    private String contrasena;

    @SerializedName("IdTipoUsu")
    @Expose
    private byte idTipoUsu;

    @SerializedName("Nombre1")
    @Expose
    private String nombre;

    @SerializedName("ApePaterno")
    @Expose
    private String apePaterno;

    @SerializedName("Celular")
    @Expose
    private int celular;

    @SerializedName("Token")
    @Expose
    private String token;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

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

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
