package com.plazoleta.restaurantes.application.factory;

import com.plazoleta.restaurantes.application.dto.request.RestaurantePost;
import com.plazoleta.restaurantes.dominio.modelo.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class RestauranteFactory {

    public Restaurante toDomain(RestaurantePost request) {
        return new Restaurante(
                null,
                request.getNombre(),
                request.getNit(),
                request.getDireccion(),
                request.getTelefono(),
                request.getUrlLogo(),
                request.getIdPropietario(),
                true
        );
    }
}
