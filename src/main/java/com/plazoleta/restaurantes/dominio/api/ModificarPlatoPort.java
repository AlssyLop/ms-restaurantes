package com.plazoleta.restaurantes.dominio.api;

import com.plazoleta.restaurantes.dominio.modelo.Plato;

public interface ModificarPlatoPort {
    Plato modificarPlato(Long idPlato, Integer precio, String descripcion, Long idPropietario);
}
