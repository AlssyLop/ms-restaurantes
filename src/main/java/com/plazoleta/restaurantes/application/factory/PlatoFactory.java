package com.plazoleta.restaurantes.application.factory;

import com.plazoleta.restaurantes.application.dto.request.CrearPlatoRequest;
import com.plazoleta.restaurantes.dominio.modelo.Plato;
import org.springframework.stereotype.Component;

@Component
public class PlatoFactory {

    public Plato toDomain(CrearPlatoRequest request) {
        return new Plato(
                null,
                request.getNombre(),
                request.getPrecio(),
                request.getDescripcion(),
                request.getUrlImagen(),
                request.getCategoria(),
                true,
                null
        );
    }
}
