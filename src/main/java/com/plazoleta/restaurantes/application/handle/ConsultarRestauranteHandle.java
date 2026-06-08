package com.plazoleta.restaurantes.application.handle;

import com.plazoleta.restaurantes.application.dto.response.RestauranteInfoResponse;
import com.plazoleta.restaurantes.dominio.modelo.Restaurante;
import com.plazoleta.restaurantes.dominio.spi.RestauranteRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsultarRestauranteHandle {

    private final RestauranteRepositoryPort restauranteRepository;

    public ConsultarRestauranteHandle(RestauranteRepositoryPort restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    public Optional<RestauranteInfoResponse> obtenerPorPropietario(Long idPropietario) {
        return restauranteRepository.findByIdPropietario(idPropietario)
                .map(r -> new RestauranteInfoResponse(r.getId(), r.getNombre().getValor()));
    }

    public Optional<RestauranteInfoResponse> obtenerPorId(Long id) {
        return restauranteRepository.findById(id)
                .map(r -> new RestauranteInfoResponse(r.getId(), r.getNombre().getValor()));
    }
}
