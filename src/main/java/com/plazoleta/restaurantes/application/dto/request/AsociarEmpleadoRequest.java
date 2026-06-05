package com.plazoleta.restaurantes.application.dto.request;

public class AsociarEmpleadoRequest {

    private Long idEmpleado;
    private Long idCargo;

    public AsociarEmpleadoRequest() {}

    public AsociarEmpleadoRequest(Long idEmpleado, Long idCargo) {
        this.idEmpleado = idEmpleado;
        this.idCargo = idCargo;
    }

    public Long getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(Long idEmpleado) { this.idEmpleado = idEmpleado; }

    public Long getIdCargo() { return idCargo; }
    public void setIdCargo(Long idCargo) { this.idCargo = idCargo; }
}
