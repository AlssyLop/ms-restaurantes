package com.plazoleta.restaurantes.application.dto.response;

public class AsociarEmpleadoResponse {

    private String mensaje;

    public AsociarEmpleadoResponse() {}

    public AsociarEmpleadoResponse(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
