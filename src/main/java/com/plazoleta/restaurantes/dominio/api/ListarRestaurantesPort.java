package com.plazoleta.restaurantes.dominio.api;

import com.plazoleta.restaurantes.dominio.modelo.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarRestaurantesPort {
    Page<Restaurante> listarRestaurantes(Pageable pageable);
}
