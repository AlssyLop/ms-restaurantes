package com.plazoleta.restaurantes.dominio.spi;

import com.plazoleta.restaurantes.dominio.modelo.EmpleadoRestaurante;
import java.util.Optional;

public interface EmpleadoRestauranteRepositoryPort {
    EmpleadoRestaurante save(EmpleadoRestaurante empleadoRestaurante);
    boolean existsByEmpleadoAndRestaurante(Long idEmpleado, Long idRestaurante);
    Optional<EmpleadoRestaurante> findByIdEmpleado(Long idEmpleado);
}
