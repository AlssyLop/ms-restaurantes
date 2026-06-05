package com.plazoleta.restaurantes.application.dto.response;

public class GestionarPlatoResponse {

    private String mensaje;

    public GestionarPlatoResponse() {}

    public GestionarPlatoResponse(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
