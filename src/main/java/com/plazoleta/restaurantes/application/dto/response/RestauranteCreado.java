package com.plazoleta.restaurantes.application.dto.response;

public class RestauranteCreado {

    private String mensaje;

    public RestauranteCreado() {}

    public RestauranteCreado(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
