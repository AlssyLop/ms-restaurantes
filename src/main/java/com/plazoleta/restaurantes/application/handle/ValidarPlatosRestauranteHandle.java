package com.plazoleta.restaurantes.application.handle;

import com.plazoleta.restaurantes.application.dto.response.PlatoInfoResponse;
import com.plazoleta.restaurantes.dominio.modelo.Plato;
import com.plazoleta.restaurantes.dominio.spi.PlatoRepositoryPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ValidarPlatosRestauranteHandle {

    private final PlatoRepositoryPort platoRepository;

    public ValidarPlatosRestauranteHandle(PlatoRepositoryPort platoRepository) {
        this.platoRepository = platoRepository;
    }

    public List<PlatoInfoResponse> consultarPlatosInfo(Long idRestaurante, List<Long> idsPlatos) {
        List<Plato> platos = platoRepository.findByIdRestauranteAndIdIn(idRestaurante, idsPlatos);
        return platos.stream()
                .map(p -> new PlatoInfoResponse(p.getId(), p.getNombre().getValor(), p.isActivo()))
                .toList();
    }
}
