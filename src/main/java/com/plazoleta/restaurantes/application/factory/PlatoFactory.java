package com.plazoleta.restaurantes.application.factory;

import com.plazoleta.restaurantes.application.dto.request.CrearPlatoRequest;
import com.plazoleta.restaurantes.dominio.modelo.Plato;
import com.plazoleta.restaurantes.dominio.modelo.value.*;
import org.springframework.stereotype.Component;

@Component
public class PlatoFactory {

    public Plato toDomain(CrearPlatoRequest request) {
        NombrePlato nombre;
        PrecioPlato precio;
        DescripcionPlato descripcion;
        UrlImagen urlImagen;
        CategoriaPlato categoria;

        try { nombre = new NombrePlato(request.getNombre()); }
        catch (IllegalArgumentException e) { throw new IllegalArgumentException("nombre: " + e.getMessage()); }

        try { precio = new PrecioPlato(request.getPrecio()); }
        catch (IllegalArgumentException e) { throw new IllegalArgumentException("precio: " + e.getMessage()); }

        try { descripcion = new DescripcionPlato(request.getDescripcion()); }
        catch (IllegalArgumentException e) { throw new IllegalArgumentException("descripcion: " + e.getMessage()); }

        try { urlImagen = new UrlImagen(request.getUrlImagen()); }
        catch (IllegalArgumentException e) { throw new IllegalArgumentException("urlImagen: " + e.getMessage()); }

        try { categoria = new CategoriaPlato(request.getCategoria()); }
        catch (IllegalArgumentException e) { throw new IllegalArgumentException("categoria: " + e.getMessage()); }

        return new Plato(null, nombre, precio, descripcion, urlImagen, categoria, true, null);
    }
}
