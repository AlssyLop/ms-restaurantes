package com.plazoleta.restaurantes.application.handle;

import com.plazoleta.restaurantes.dominio.spi.PlatoRepositoryPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ValidarPlatosRestauranteHandle {

    private final PlatoRepositoryPort platoRepository;

    public ValidarPlatosRestauranteHandle(PlatoRepositoryPort platoRepository) {
        this.platoRepository = platoRepository;
    }

    public List<Long> validarPertenencia(Long idRestaurante, List<Long> idsPlatos) {
        return platoRepository.findIdsByIdRestauranteAndIdIn(idRestaurante, idsPlatos);
    }

    public List<Long> validarActivos(List<Long> idsPlatos) {
        return platoRepository.findIdsByIdInAndActivoTrue(idsPlatos);
    }
}
