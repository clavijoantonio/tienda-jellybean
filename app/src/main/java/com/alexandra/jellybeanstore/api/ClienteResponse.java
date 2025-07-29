package com.alexandra.jellybeanstore.api;

import com.google.gson.annotations.SerializedName;

public class ClienteResponse {
    @SerializedName("idCliente")
    private long idCliente;
    private String nombre;
    private String apellido;
    private String identificacion;

    //construtor para la devolucion del pedido creardo en la base de datos


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
