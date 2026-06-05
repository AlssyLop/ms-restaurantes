package com.plazoleta.restaurantes.dominio.api;

import com.plazoleta.restaurantes.dominio.modelo.Restaurante;

public interface CrearRestaurantePort {
    Restaurante crearRestaurante(Restaurante restaurante);
}
