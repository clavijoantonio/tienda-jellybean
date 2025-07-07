package com.alexandra.jellybeanstore.models;

import java.util.List;

public class Pedido {
    private String numeroPedido;
    private long clienteId;
    private List<DetallePedido> detalles;
    //construtor para la devolucion del pedido creardo en la base de datos
    public Pedido(String numeroPedido, long clienteId, List<DetallePedido> detalles) {
        this.numeroPedido = numeroPedido;
        this.clienteId = clienteId;
        this.detalles = detalles;
    }

    //construtor para la creacion el pedido
    public Pedido( long clienteId, List<DetallePedido> detalles) {
        this.numeroPedido = numeroPedido;
        this.clienteId = clienteId;
        this.detalles = detalles;
    }
   public Pedido(){
   }
    // Getters
    public String getNumeroPedido() { return numeroPedido; }
    public long getClienteId() { return clienteId; }
    public List<DetallePedido> getDetalles() { return detalles; }
}

