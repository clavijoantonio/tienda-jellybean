package com.alexandra.jellybeanstore.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("nombreProducto")
    private String nombreProducto;
    @SerializedName("descripcionProducto")
    private String descripcionProducto;
    @SerializedName("precioProducto")
    private double precioProducto;
    @SerializedName("cantidaDisponible")
    private int cantidaDisponible;
    @SerializedName("Foto")
    private String Foto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public int getCantidaDisponible() {
        return cantidaDisponible;
    }

    public void setCantidaDisponible(int cantidaDisponible) {
        this.cantidaDisponible = cantidaDisponible;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }
}
