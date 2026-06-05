package com.plazoleta.restaurantes.infrastructure.persistence.adapter;

import com.plazoleta.restaurantes.dominio.modelo.Plato;
import com.plazoleta.restaurantes.dominio.modelo.value.NombrePlato;
import com.plazoleta.restaurantes.dominio.spi.PlatoRepositoryPort;
import com.plazoleta.restaurantes.infrastructure.entity.EntidadPlato;
import com.plazoleta.restaurantes.infrastructure.persistence.mapper.IPlatoEntityMapper;
import com.plazoleta.restaurantes.infrastructure.persistence.repository.IPlatoJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PlatoRepositoryAdapter implements PlatoRepositoryPort {

    private final IPlatoJpaRepository jpaRepository;
    private final IPlatoEntityMapper mapper;

    public PlatoRepositoryAdapter(IPlatoJpaRepository jpaRepository,
                                  IPlatoEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Plato save(Plato plato) {
        EntidadPlato entity = mapper.toEntity(plato);
        entity = jpaRepository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public boolean existsByNombreAndIdRestaurante(NombrePlato nombre, Long idRestaurante) {
        return jpaRepository.existsByNombreAndIdRestaurante(nombre.getValor(), idRestaurante);
    }
}
