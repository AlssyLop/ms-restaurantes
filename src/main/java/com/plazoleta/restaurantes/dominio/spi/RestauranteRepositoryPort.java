package com.plazoleta.restaurantes.dominio.spi;

import com.plazoleta.restaurantes.dominio.modelo.Restaurante;
import com.plazoleta.restaurantes.dominio.modelo.value.Nit;
import com.plazoleta.restaurantes.dominio.modelo.value.NombreRestaurante;
import java.util.Optional;

public interface RestauranteRepositoryPort {
    Restaurante save(Restaurante restaurante);
    boolean existsByNombre(NombreRestaurante nombre);
    boolean existsByNit(Nit nit);
    Optional<Restaurante> findByIdPropietario(Long idPropietario);
}
