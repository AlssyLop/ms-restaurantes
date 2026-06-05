package com.plazoleta.restaurantes.dominio.usecase;

import com.plazoleta.restaurantes.dominio.api.ListarRestaurantesPort;
import com.plazoleta.restaurantes.dominio.modelo.Restaurante;
import com.plazoleta.restaurantes.dominio.spi.RestauranteRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ListarRestaurantes implements ListarRestaurantesPort {

    private final RestauranteRepositoryPort restauranteRepository;

    public ListarRestaurantes(RestauranteRepositoryPort restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public Page<Restaurante> listarRestaurantes(Pageable pageable) {
        return restauranteRepository.findAllOrderedByName(pageable);
    }
}
