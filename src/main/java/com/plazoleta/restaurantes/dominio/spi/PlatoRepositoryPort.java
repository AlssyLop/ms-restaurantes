package com.plazoleta.restaurantes.dominio.spi;

import com.plazoleta.restaurantes.dominio.modelo.Plato;
import com.plazoleta.restaurantes.dominio.modelo.value.NombrePlato;

import java.util.Optional;

public interface PlatoRepositoryPort {
    Plato save(Plato plato);
    boolean existsByNombreAndIdRestaurante(NombrePlato nombre, Long idRestaurante);
    Optional<Plato> findById(Long id);
}
