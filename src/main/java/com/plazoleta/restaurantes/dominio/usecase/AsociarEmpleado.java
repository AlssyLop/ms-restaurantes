package com.plazoleta.restaurantes.dominio.usecase;

import com.plazoleta.restaurantes.dominio.api.AsociarEmpleadoPort;
import com.plazoleta.restaurantes.dominio.modelo.EmpleadoRestaurante;
import com.plazoleta.restaurantes.dominio.spi.EmpleadoRestauranteRepositoryPort;

public class AsociarEmpleado implements AsociarEmpleadoPort {

    private final EmpleadoRestauranteRepositoryPort repository;

    public AsociarEmpleado(EmpleadoRestauranteRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public EmpleadoRestaurante asociar(EmpleadoRestaurante empleadoRestaurante) {
        if (repository.existsByEmpleadoAndRestaurante(
                empleadoRestaurante.getIdEmpleado(),
                empleadoRestaurante.getIdRestaurante())) {
            throw new IllegalArgumentException("El empleado ya esta asociado a este restaurante");
        }
        return repository.save(empleadoRestaurante);
    }
}
