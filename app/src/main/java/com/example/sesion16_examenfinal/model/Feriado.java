package com.example.sesion16_examenfinal.model;

import com.google.gson.annotations.SerializedName;

public class Feriado {
    private int idFeriado;
    @SerializedName("date")
    private String fecha;
    @SerializedName("countryCode")
    private String pais;
    @SerializedName("localName")
    private String nombreFeriado;

    public Feriado(int idFeriado, String fecha, String pais, String nombreFeriado) {
        this.idFeriado = idFeriado;
        this.fecha = fecha;
        this.pais = pais;
        this.nombreFeriado = nombreFeriado;
    }

    public int getIdFeriado() {
        return idFeriado;
    }

    public String getFecha() {
        return fecha;
    }

    public String getPais() {
        return pais;
    }

    public String getNombreFeriado() {
        return nombreFeriado;
    }

}
