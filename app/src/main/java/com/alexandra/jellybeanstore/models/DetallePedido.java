package com.alexandra.jellybeanstore.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class DetallePedido implements Serializable {
    @SerializedName("productoId")
    private long productoId;
    @SerializedName("cantidad")
    private int cantidad;
    @SerializedName("product")
    private transient Product product;
    public DetallePedido() {

    }
    public DetallePedido(Product product, int cantidad) {
        this.productoId= product.getIdProducto();
        this.product = product;
        this.cantidad = cantidad;
    }

    // Getters


    public long getProductoId() {
        return productoId;
    }

    public void setProductoId(long productoId) {
        this.productoId = productoId;
    }

    public int getCantidad() { return cantidad; }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetallePedido that = (DetallePedido) o;
        return productoId == that.productoId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productoId);
    }

    // Método toString para depuración
    @Override
    public String toString() {
        return "DetallePedido{" +
                "id=" + productoId +
                ", cantidad=" + cantidad +
                ", product=" + (product != null ? product.getNombreProducto() : "null") +
                '}';
    }
}

