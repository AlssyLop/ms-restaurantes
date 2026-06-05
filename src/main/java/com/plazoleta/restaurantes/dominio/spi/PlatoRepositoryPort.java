package com.plazoleta.restaurantes.dominio.spi;

import com.plazoleta.restaurantes.dominio.modelo.Plato;
import com.plazoleta.restaurantes.dominio.modelo.value.NombrePlato;

public interface PlatoRepositoryPort {
    Plato save(Plato plato);
    boolean existsByNombreAndIdRestaurante(NombrePlato nombre, Long idRestaurante);
}
