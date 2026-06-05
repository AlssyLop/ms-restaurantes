package com.plazoleta.restaurantes.infrastructure.persistence.repository;

import com.plazoleta.restaurantes.infrastructure.entity.EntidadRestaurante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRestauranteJpaRepository extends JpaRepository<EntidadRestaurante, Long> {
    boolean existsByNombre(String nombre);
    boolean existsByNit(String nit);
}
