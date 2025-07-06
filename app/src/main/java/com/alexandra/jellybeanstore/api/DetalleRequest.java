package com.alexandra.jellybeanstore.api;

public class DetalleRequest {
    private long productoId;
    private int cantidad;

    public DetalleRequest(long productoId, int cantidad) {
        this.productoId = productoId;
        this.cantidad = cantidad;
    }
}
