package com.alexandra.jellybeanstore.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DetallePedido implements Serializable {
    @SerializedName("id")
    private long id;
    @SerializedName("cantidad")
    private int cantidad;
    @SerializedName("product")
    private transient Product product;
    public DetallePedido() {

    }
    public DetallePedido(Product product, int cantidad) {
        this.id= product.getIdProducto();
        this.product = product;
        this.cantidad = cantidad;
    }

    // Getters
    public long getId() { return id; }
    public int getCantidad() { return cantidad; }

    public void setId(long id) {
        this.id = id;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    // Método toString para depuración
    @Override
    public String toString() {
        return "DetallePedido{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                ", product=" + (product != null ? product.getNombreProducto() : "null") +
                '}';
    }
}

