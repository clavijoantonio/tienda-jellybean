package com.alexandra.jellybeanstore.api;

import com.alexandra.jellybeanstore.models.DetallePedido;

import java.util.List;

public class PedidoResponse {
    private long clienteId;
    private List<DetallePedido> detalles;

    public PedidoResponse(long clienteId, List<DetallePedido> detalles) {
        this.clienteId = clienteId;
        this.detalles = detalles;
    }

    // Getters
    public long getClienteId() {
        return clienteId;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }
}
