package com.plazoleta.restaurantes.infrastructure.persistence.mapper;

import com.plazoleta.restaurantes.dominio.modelo.EmpleadoRestaurante;
import com.plazoleta.restaurantes.infrastructure.entity.EntidadEmpleadoRestaurante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IEmpleadoRestauranteEntityMapper {

    @Mapping(target = "id", source = "domain.id")
    @Mapping(target = "idEmpleado", source = "domain.idEmpleado")
    @Mapping(target = "idRestaurante", source = "domain.idRestaurante")
    @Mapping(target = "idCargo", source = "domain.idCargo")
    @Mapping(target = "activo", source = "domain.activo")
    @Mapping(target = "fechaAsignacion", ignore = true)
    EntidadEmpleadoRestaurante toEntity(EmpleadoRestaurante domain);

    default EmpleadoRestaurante toDomain(EntidadEmpleadoRestaurante entity) {
        if (entity == null) return null;
        return new EmpleadoRestaurante(
                entity.getId(),
                entity.getIdEmpleado(),
                entity.getIdRestaurante(),
                entity.getIdCargo(),
                entity.isActivo(),
                entity.getFechaAsignacion()
        );
    }
}
