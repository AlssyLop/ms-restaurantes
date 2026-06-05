package com.plazoleta.restaurantes.application.dto.response;

public class EmpleadoRestauranteResponse {

    private Long idEmpleado;
    private Long idRestaurante;
    private Long idCargo;

    public EmpleadoRestauranteResponse() {}

    public EmpleadoRestauranteResponse(Long idEmpleado, Long idRestaurante, Long idCargo) {
        this.idEmpleado = idEmpleado;
        this.idRestaurante = idRestaurante;
        this.idCargo = idCargo;
    }

    public Long getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(Long idEmpleado) { this.idEmpleado = idEmpleado; }

    public Long getIdRestaurante() { return idRestaurante; }
    public void setIdRestaurante(Long idRestaurante) { this.idRestaurante = idRestaurante; }

    public Long getIdCargo() { return idCargo; }
    public void setIdCargo(Long idCargo) { this.idCargo = idCargo; }
}