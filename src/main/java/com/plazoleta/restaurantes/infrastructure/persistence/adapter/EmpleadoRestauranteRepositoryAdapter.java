package com.plazoleta.restaurantes.infrastructure.persistence.adapter;

import com.plazoleta.restaurantes.dominio.modelo.EmpleadoRestaurante;
import com.plazoleta.restaurantes.dominio.spi.EmpleadoRestauranteRepositoryPort;
import com.plazoleta.restaurantes.infrastructure.entity.EntidadEmpleadoRestaurante;
import com.plazoleta.restaurantes.infrastructure.persistence.mapper.IEmpleadoRestauranteEntityMapper;
import com.plazoleta.restaurantes.infrastructure.persistence.repository.IEmpleadoRestauranteJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class EmpleadoRestauranteRepositoryAdapter implements EmpleadoRestauranteRepositoryPort {

    private final IEmpleadoRestauranteJpaRepository jpaRepository;
    private final IEmpleadoRestauranteEntityMapper mapper;

    public EmpleadoRestauranteRepositoryAdapter(IEmpleadoRestauranteJpaRepository jpaRepository,
                                                 IEmpleadoRestauranteEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public EmpleadoRestaurante save(EmpleadoRestaurante empleadoRestaurante) {
        EntidadEmpleadoRestaurante entity = mapper.toEntity(empleadoRestaurante);
        entity = jpaRepository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public boolean existsByEmpleadoAndRestaurante(Long idEmpleado, Long idRestaurante) {
        return jpaRepository.existsByIdEmpleadoAndIdRestaurante(idEmpleado, idRestaurante);
    }
}
