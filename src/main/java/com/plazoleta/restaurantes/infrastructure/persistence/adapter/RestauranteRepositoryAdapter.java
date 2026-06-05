package com.plazoleta.restaurantes.infrastructure.persistence.adapter;

import com.plazoleta.restaurantes.dominio.modelo.Restaurante;
import com.plazoleta.restaurantes.dominio.modelo.value.Nit;
import com.plazoleta.restaurantes.dominio.modelo.value.NombreRestaurante;
import com.plazoleta.restaurantes.dominio.spi.RestauranteRepositoryPort;
import com.plazoleta.restaurantes.infrastructure.entity.EntidadRestaurante;
import com.plazoleta.restaurantes.infrastructure.persistence.mapper.IRestauranteEntityMapper;
import com.plazoleta.restaurantes.infrastructure.persistence.repository.IRestauranteJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RestauranteRepositoryAdapter implements RestauranteRepositoryPort {

    private final IRestauranteJpaRepository jpaRepository;
    private final IRestauranteEntityMapper mapper;

    public RestauranteRepositoryAdapter(IRestauranteJpaRepository jpaRepository,
                                        IRestauranteEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Restaurante save(Restaurante restaurante) {
        EntidadRestaurante entity = mapper.toEntity(restaurante);
        entity = jpaRepository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public boolean existsByNombre(NombreRestaurante nombre) {
        return jpaRepository.existsByNombre(nombre.getValor());
    }

    @Override
    public boolean existsByNit(Nit nit) {
        return jpaRepository.existsByNit(nit.getValor());
    }
}
