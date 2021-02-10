package com.hache.appentrepatas.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;

public class PerroDTO {
    @SerializedName("nombrePerro")
    @Expose
    private String nombre;

    @SerializedName("sexoPerro")
    @Expose
    private String sexo;

    @SerializedName("pesoPerro")
    @Expose
    private String peso;

    @SerializedName("edadPerro")
    @Expose
    private String edad;

    @SerializedName("imgPerro")
    @Expose
    private String imagen;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}