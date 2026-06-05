package com.plazoleta.restaurantes.application.handle;

import com.plazoleta.restaurantes.application.dto.request.RestaurantePost;
import com.plazoleta.restaurantes.application.dto.response.RestauranteCreado;
import com.plazoleta.restaurantes.application.factory.RestauranteFactory;
import com.plazoleta.restaurantes.dominio.api.CrearRestaurantePort;
import com.plazoleta.restaurantes.dominio.modelo.Restaurante;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RestauranteHandle {

    private final CrearRestaurantePort crearRestaurantePort;
    private final RestauranteFactory restauranteFactory;

    public RestauranteHandle(CrearRestaurantePort crearRestaurantePort,
                             RestauranteFactory restauranteFactory) {
        this.crearRestaurantePort = crearRestaurantePort;
        this.restauranteFactory = restauranteFactory;
    }

    public RestauranteCreado crearRestaurante(RestaurantePost request) {
        Restaurante restaurante = restauranteFactory.toDomain(request);
        crearRestaurantePort.crearRestaurante(restaurante);
        return new RestauranteCreado("Restaurante creado exitosamente");
    }
}
