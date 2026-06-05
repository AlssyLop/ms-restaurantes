package com.plazoleta.restaurantes.dominio.spi;

import com.plazoleta.restaurantes.dominio.modelo.Plato;
import com.plazoleta.restaurantes.dominio.modelo.value.NombrePlato;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlatoRepositoryPort {
    Plato save(Plato plato);
    boolean existsByNombreAndIdRestaurante(NombrePlato nombre, Long idRestaurante);
    Optional<Plato> findById(Long id);
    Page<Plato> findByIdRestauranteAndActivoTrue(Long idRestaurante, Pageable pageable);
    Page<Plato> findByIdRestauranteAndActivoTrueAndCategoria(Long idRestaurante, String categoria, Pageable pageable);
}
