package com.plazoleta.restaurantes.dominio.spi;

import com.plazoleta.restaurantes.dominio.modelo.UsuarioRestaurante;
import java.util.Optional;

public interface UsuarioValidacionPort {
    Optional<UsuarioRestaurante> consultarPorId(Long id);
}
