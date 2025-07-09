package com.alexandra.jellybeanstore.api;

public class ClienteRequest {
    private long clienteId;
    private String nombre;
    private String apellido;

    private String identificacion;

    //construtor para la devolucion del pedido creardo en la base de datos

    public ClienteRequest(long clienteId, String nombre, String apellido, String identificacion) {
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
    }

    public ClienteRequest(String nombre, String identificacion) {
        this.nombre = nombre;
        this.identificacion = identificacion;
    }
}
