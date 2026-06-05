package com.plazoleta.restaurantes.dominio.spi;

import com.plazoleta.restaurantes.dominio.modelo.Restaurante;
import com.plazoleta.restaurantes.dominio.modelo.value.NombreRestaurante;
import com.plazoleta.restaurantes.dominio.modelo.value.Nit;

public interface RestauranteRepositoryPort {
    Restaurante save(Restaurante restaurante);
    boolean existsByNombre(NombreRestaurante nombre);
    boolean existsByNit(Nit nit);
}
