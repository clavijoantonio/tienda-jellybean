package com.alexandra.jellybeanstore.api;

public class ClienteResponse {
    private long clienteId;
    private String nombre;
    private String apellido;

    private String identificacion;

    //construtor para la devolucion del pedido creardo en la base de datos

    public ClienteResponse(long clienteId, String nombre, String apellido, String identificacion) {
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
    }
}
