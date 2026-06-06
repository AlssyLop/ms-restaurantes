package com.plazoleta.restaurantes.application.factory;

import com.plazoleta.restaurantes.application.dto.request.RestaurantePost;
import com.plazoleta.restaurantes.dominio.modelo.Restaurante;
import com.plazoleta.restaurantes.dominio.modelo.value.*;
import org.springframework.stereotype.Component;

@Component
public class RestauranteFactory {

    public Restaurante toDomain(RestaurantePost request) {
        NombreRestaurante nombre;
        Nit nit;
        Telefono telefono;
        UrlLogo urlLogo;

        try { nombre = new NombreRestaurante(request.getNombre()); }
        catch (IllegalArgumentException e) { throw new IllegalArgumentException("nombre: " + e.getMessage()); }

        try { nit = new Nit(request.getNit()); }
        catch (IllegalArgumentException e) { throw new IllegalArgumentException("nit: " + e.getMessage()); }

        try { telefono = new Telefono(request.getTelefono()); }
        catch (IllegalArgumentException e) { throw new IllegalArgumentException("telefono: " + e.getMessage()); }

        try { urlLogo = new UrlLogo(request.getUrlLogo()); }
        catch (IllegalArgumentException e) { throw new IllegalArgumentException("urlLogo: " + e.getMessage()); }

        return new Restaurante(
                null, nombre, nit, request.getDireccion(), telefono, urlLogo,
                request.getIdPropietario(), true
        );
    }
}
