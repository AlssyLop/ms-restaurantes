package com.plazoleta.restaurantes.infrastructure.persistence.repository;

import com.plazoleta.restaurantes.infrastructure.entity.EntidadPlato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlatoJpaRepository extends JpaRepository<EntidadPlato, Long> {
    boolean existsByNombreAndIdRestaurante(String nombre, Long idRestaurante);
}
