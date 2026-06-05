package com.plazoleta.restaurantes.application.handle;

import com.plazoleta.restaurantes.application.dto.response.RestauranteListadoResponse;
import com.plazoleta.restaurantes.application.dto.response.RestaurantePageResponse;
import com.plazoleta.restaurantes.dominio.api.ListarRestaurantesPort;
import com.plazoleta.restaurantes.dominio.modelo.Restaurante;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ListarRestaurantesHandle {

    private final ListarRestaurantesPort listarRestaurantesPort;

    public ListarRestaurantesHandle(ListarRestaurantesPort listarRestaurantesPort) {
        this.listarRestaurantesPort = listarRestaurantesPort;
    }

    public RestaurantePageResponse listarRestaurantes(int page, int size) {
        Page<Restaurante> pageResult = listarRestaurantesPort.listarRestaurantes(
                PageRequest.of(page, size));

        return new RestaurantePageResponse(
                pageResult.getContent().stream()
                        .map(r -> new RestauranteListadoResponse(
                                r.getNombre().getValor(),
                                r.getUrlLogo().getValor()))
                        .collect(Collectors.toList()),
                pageResult.getNumber(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        );
    }
}
