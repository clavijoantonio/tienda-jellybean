package com.alexandra.jellybeanstore.models;

public class DetallePedido {
    private long productoId;
    private int cantidad;

    public DetallePedido(Product product, int cantidad) {
        this.productoId = product.getId();
        this.cantidad = cantidad;
    }

    // Getters
    public long getProductoId() { return productoId; }
    public int getCantidad() { return cantidad; }
}

