package com.plazoleta.restaurantes.dominio.api;

import com.plazoleta.restaurantes.dominio.modelo.Plato;

public interface HabilitarDeshabilitarPlatoPort {
    Plato habilitar(Long idPlato, Long idPropietario);
    Plato deshabilitar(Long idPlato, Long idPropietario);
}
