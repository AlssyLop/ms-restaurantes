package com.plazoleta.restaurantes.infrastructure.persistence.repository;

import com.plazoleta.restaurantes.infrastructure.entity.EntidadEmpleadoRestaurante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmpleadoRestauranteJpaRepository extends JpaRepository<EntidadEmpleadoRestaurante, Long> {
    boolean existsByIdEmpleadoAndIdRestaurante(Long idEmpleado, Long idRestaurante);
}
