package com.plazoleta.restaurantes.infrastructure.persistence.repository;

import com.plazoleta.restaurantes.infrastructure.entity.EntidadEmpleadoRestaurante;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmpleadoRestauranteJpaRepository extends JpaRepository<EntidadEmpleadoRestaurante, Long> {
    boolean existsByIdEmpleadoAndIdRestaurante(Long idEmpleado, Long idRestaurante);
    Optional<EntidadEmpleadoRestaurante> findByIdEmpleado(Long idEmpleado);
}
