package com.plazoleta.restaurantes.application.handle;

import com.plazoleta.restaurantes.application.dto.response.PlatoListadoResponse;
import com.plazoleta.restaurantes.application.dto.response.PlatoPageResponse;
import com.plazoleta.restaurantes.dominio.api.ListarPlatosRestaurantePort;
import com.plazoleta.restaurantes.dominio.modelo.Plato;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ListarPlatosRestauranteHandle {

    private final ListarPlatosRestaurantePort listarPlatosRestaurantePort;

    public ListarPlatosRestauranteHandle(ListarPlatosRestaurantePort listarPlatosRestaurantePort) {
        this.listarPlatosRestaurantePort = listarPlatosRestaurantePort;
    }

    public PlatoPageResponse listarPlatos(Long idRestaurante, String categoria, int page, int size) {
        Page<Plato> pageResult = listarPlatosRestaurantePort.listarPlatosPorRestaurante(
                idRestaurante, categoria, PageRequest.of(page, size));

        return new PlatoPageResponse(
                pageResult.getContent().stream()
                        .map(p -> new PlatoListadoResponse(
                                p.getNombre().getValor(),
                                p.getPrecio().getValor(),
                                p.getDescripcion().getValor(),
                                p.getUrlImagen().getValor(),
                                p.getCategoria().getValor()))
                        .collect(Collectors.toList()),
                pageResult.getNumber(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        );
    }
}
