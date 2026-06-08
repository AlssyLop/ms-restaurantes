package com.plazoleta.restaurantes.application.dto.request;

public class ModificarPlatoRequest {

    private Integer precio;
    private String descripcion;

    public ModificarPlatoRequest() {}

    public ModificarPlatoRequest(Integer precio, String descripcion) {
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public Integer getPrecio() { return precio; }
    public void setPrecio(Integer precio) { this.precio = precio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
