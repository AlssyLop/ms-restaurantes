package com.plazoleta.restaurantes.dominio.api;

import com.plazoleta.restaurantes.dominio.modelo.EmpleadoRestaurante;

public interface AsociarEmpleadoPort {
    EmpleadoRestaurante asociar(EmpleadoRestaurante empleadoRestaurante);
}
