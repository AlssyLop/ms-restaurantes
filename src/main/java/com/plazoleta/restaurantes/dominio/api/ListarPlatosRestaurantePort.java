package com.plazoleta.restaurantes.dominio.api;

import com.plazoleta.restaurantes.dominio.modelo.Plato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarPlatosRestaurantePort {
    Page<Plato> listarPlatosPorRestaurante(Long idRestaurante, String categoria, Pageable pageable);
}
