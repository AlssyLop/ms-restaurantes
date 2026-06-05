package com.plazoleta.restaurantes.dominio.usecase;

import com.plazoleta.restaurantes.application.exception.RestauranteNoEncontradoException;
import com.plazoleta.restaurantes.dominio.api.ListarPlatosRestaurantePort;
import com.plazoleta.restaurantes.dominio.modelo.Plato;
import com.plazoleta.restaurantes.dominio.spi.PlatoRepositoryPort;
import com.plazoleta.restaurantes.dominio.spi.RestauranteRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ListarPlatosRestaurante implements ListarPlatosRestaurantePort {

    private final PlatoRepositoryPort platoRepository;
    private final RestauranteRepositoryPort restauranteRepository;

    public ListarPlatosRestaurante(PlatoRepositoryPort platoRepository,
                                   RestauranteRepositoryPort restauranteRepository) {
        this.platoRepository = platoRepository;
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public Page<Plato> listarPlatosPorRestaurante(Long idRestaurante, String categoria, Pageable pageable) {
        restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new RestauranteNoEncontradoException(
                        "El restaurante con id " + idRestaurante + " no existe"));

        if (categoria != null && !categoria.isBlank()) {
            return platoRepository.findByIdRestauranteAndActivoTrueAndCategoria(
                    idRestaurante, categoria.trim(), pageable);
        }
        return platoRepository.findByIdRestauranteAndActivoTrue(idRestaurante, pageable);
    }
}
