package com.plazoleta.restaurantes.application.dto.response;

public class CrearPlatoResponse {

    private String mensaje;

    public CrearPlatoResponse() {}

    public CrearPlatoResponse(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
