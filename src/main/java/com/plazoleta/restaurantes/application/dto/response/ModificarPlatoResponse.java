package com.plazoleta.restaurantes.application.dto.response;

public class ModificarPlatoResponse {

    private String mensaje;

    public ModificarPlatoResponse() {}

    public ModificarPlatoResponse(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
