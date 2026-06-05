package com.plazoleta.restaurantes.dominio.spi;

import com.plazoleta.restaurantes.dominio.modelo.EmpleadoRestaurante;

public interface EmpleadoRestauranteRepositoryPort {
    EmpleadoRestaurante save(EmpleadoRestaurante empleadoRestaurante);
    boolean existsByEmpleadoAndRestaurante(Long idEmpleado, Long idRestaurante);
}
