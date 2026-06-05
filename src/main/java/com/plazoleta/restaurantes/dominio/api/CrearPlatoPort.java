package com.plazoleta.restaurantes.dominio.api;

import com.plazoleta.restaurantes.dominio.modelo.Plato;

public interface CrearPlatoPort {
    Plato crearPlato(Plato plato, Long idPropietario);
}
