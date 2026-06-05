package com.plazoleta.restaurantes.dominio.spi;

import com.plazoleta.restaurantes.dominio.modelo.Restaurante;
import com.plazoleta.restaurantes.dominio.modelo.value.Nit;
import com.plazoleta.restaurantes.dominio.modelo.value.NombreRestaurante;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestauranteRepositoryPort {
    Restaurante save(Restaurante restaurante);
    boolean existsByNombre(NombreRestaurante nombre);
    boolean existsByNit(Nit nit);
    Optional<Restaurante> findByIdPropietario(Long idPropietario);
    Page<Restaurante> findAllOrderedByName(Pageable pageable);
    Optional<Restaurante> findById(Long id);
}
