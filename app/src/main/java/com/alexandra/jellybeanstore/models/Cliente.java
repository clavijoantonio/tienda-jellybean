package com.alexandra.jellybeanstore.models;

import com.alexandra.jellybeanstore.api.ClienteResponse;

import java.util.List;

public class Cliente {

    private long idCliente;
    private String nombre;
    private String apellido;

    private String identificacion;

    //construtor para la devolucion del pedido creardo en la base de datos

    public Cliente(ClienteResponse clienteResponse) {
        this.idCliente = clienteResponse.getIdCliente();
        this.nombre = clienteResponse.getNombre();
        this.apellido = clienteResponse.getApellido();
        this.identificacion = clienteResponse.getIdentificacion();    }


    //construtor para la creacion el pedido

   public Cliente(){
   }
    // Getters


    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
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

