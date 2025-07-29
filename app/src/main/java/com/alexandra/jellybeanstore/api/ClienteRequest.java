package com.alexandra.jellybeanstore.api;

public class ClienteRequest {
    private long clienteId;
    private String nombreCliente;
    private String apellido;

    private String identificacion;

    //construtor para la devolucion del pedido creardo en la base de datos

    public ClienteRequest(long clienteId, String nombreCliente, String apellido, String identificacion) {
        this.clienteId = clienteId;
        this.nombreCliente = nombreCliente;
        this.apellido = apellido;
        this.identificacion = identificacion;
    }

    public ClienteRequest(String nombreCliente, String identificacion) {
        this.nombreCliente = nombreCliente;
        this.identificacion = identificacion;
    }
}
