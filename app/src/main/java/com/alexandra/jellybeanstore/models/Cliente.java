package com.alexandra.jellybeanstore.models;

import java.util.List;

public class Cliente {

    private long clienteId;
    private String nombre;
    private String apellido;

    private String identificacion;

    //construtor para la devolucion del pedido creardo en la base de datos

    public Cliente(long clienteId, String nombre, String apellido, String identificacion) {
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
    }


    //construtor para la creacion el pedido

   public Cliente(){
   }
    // Getters

    public long getClienteId() {
        return clienteId;
    }

    public void setClienteId(long clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
}

