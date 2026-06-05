package com.plazoleta.restaurantes.infrastructure.persistence.mapper;

import com.plazoleta.restaurantes.dominio.modelo.Restaurante;
import com.plazoleta.restaurantes.dominio.modelo.value.Nit;
import com.plazoleta.restaurantes.dominio.modelo.value.NombreRestaurante;
import com.plazoleta.restaurantes.dominio.modelo.value.Telefono;
import com.plazoleta.restaurantes.dominio.modelo.value.UrlLogo;
import com.plazoleta.restaurantes.infrastructure.entity.EntidadRestaurante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IRestauranteEntityMapper {

    @Mapping(target = "id", source = "domain.id")
    @Mapping(target = "nombre", source = "domain.nombre.valor")
    @Mapping(target = "nit", source = "domain.nit.valor")
    @Mapping(target = "telefono", source = "domain.telefono.valor")
    @Mapping(target = "urlLogo", source = "domain.urlLogo.valor")
    @Mapping(target = "fechaCreacion", ignore = true)
    EntidadRestaurante toEntity(Restaurante domain);

    default Restaurante toDomain(EntidadRestaurante entity) {
        if (entity == null) return null;
        return new Restaurante(
                entity.getId(),
                new NombreRestaurante(entity.getNombre()),
                new Nit(entity.getNit()),
                entity.getDireccion(),
                new Telefono(entity.getTelefono()),
                new UrlLogo(entity.getUrlLogo()),
                entity.getIdPropietario(),
                entity.isActivo()
        );
    }
}
