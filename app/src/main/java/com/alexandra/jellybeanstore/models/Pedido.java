package com.alexandra.jellybeanstore.models;

import java.util.List;

public class Pedido {
    private String numeroPedido;
    private long idCliente;
    private List<DetallePedido> detalles;
    //construtor para la devolucion del pedido creardo en la base de datos
    public Pedido(String numeroPedido, long idCliente, List<DetallePedido> detalles) {
        this.numeroPedido = numeroPedido;
        this.idCliente = idCliente;
        this.detalles = detalles;
    }

    //construtor para la creacion el pedido
    public Pedido( long idCliente, List<DetallePedido> detalles) {
        this.numeroPedido = numeroPedido;
        this.idCliente = idCliente;
        this.detalles = detalles;
    }
    public Pedido( long idCliente) {
        this.idCliente = idCliente;
    }
   public Pedido(){
   }
    // Getters
    public String getNumeroPedido() { return numeroPedido; }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public List<DetallePedido> getDetalles() { return detalles; }
}

