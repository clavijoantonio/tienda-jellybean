package com.alexandra.jellybeanstore.api;

import java.util.List;

public class PedidoRequest {
    private String numeroPedido;
    private long clienteId;
    private List<DetalleRequest> detalles;

    public PedidoRequest(String numeroPedido, long clienteId, List<DetalleRequest> detalles) {
        this.numeroPedido = numeroPedido;
        this.clienteId = clienteId;
        this.detalles = detalles;
    }
}
