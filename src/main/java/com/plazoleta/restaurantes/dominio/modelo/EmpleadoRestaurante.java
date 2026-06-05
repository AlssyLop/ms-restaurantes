package com.plazoleta.restaurantes.dominio.modelo;

import java.time.LocalDateTime;

public class EmpleadoRestaurante {
    private Long id;
    private Long idEmpleado;
    private Long idRestaurante;
    private Long idCargo;
    private boolean activo;
    private LocalDateTime fechaAsignacion;

    public EmpleadoRestaurante() {}

    public EmpleadoRestaurante(Long id, Long idEmpleado, Long idRestaurante,
                               Long idCargo, boolean activo, LocalDateTime fechaAsignacion) {
        this.id = id;
        this.idEmpleado = idEmpleado;
        this.idRestaurante = idRestaurante;
        this.idCargo = idCargo;
        this.activo = activo;
        this.fechaAsignacion = fechaAsignacion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(Long idEmpleado) { this.idEmpleado = idEmpleado; }

    public Long getIdRestaurante() { return idRestaurante; }
    public void setIdRestaurante(Long idRestaurante) { this.idRestaurante = idRestaurante; }

    public Long getIdCargo() { return idCargo; }
    public void setIdCargo(Long idCargo) { this.idCargo = idCargo; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public LocalDateTime getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(LocalDateTime fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }
}
