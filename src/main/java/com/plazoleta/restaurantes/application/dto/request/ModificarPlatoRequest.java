package com.plazoleta.restaurantes.application.dto.request;

public class ModificarPlatoRequest {

    private Integer precio;
    private String descripcion;
    private Long idPropietario;

    public ModificarPlatoRequest() {}

    public ModificarPlatoRequest(Integer precio, String descripcion, Long idPropietario) {
        this.precio = precio;
        this.descripcion = descripcion;
        this.idPropietario = idPropietario;
    }

    public Integer getPrecio() { return precio; }
    public void setPrecio(Integer precio) { this.precio = precio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Long getIdPropietario() { return idPropietario; }
    public void setIdPropietario(Long idPropietario) { this.idPropietario = idPropietario; }
}
