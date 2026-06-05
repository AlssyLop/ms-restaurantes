package com.plazoleta.restaurantes.infrastructure.persistence.mapper;

import com.plazoleta.restaurantes.dominio.modelo.Plato;
import com.plazoleta.restaurantes.dominio.modelo.value.CategoriaPlato;
import com.plazoleta.restaurantes.dominio.modelo.value.DescripcionPlato;
import com.plazoleta.restaurantes.dominio.modelo.value.NombrePlato;
import com.plazoleta.restaurantes.dominio.modelo.value.PrecioPlato;
import com.plazoleta.restaurantes.dominio.modelo.value.UrlImagen;
import com.plazoleta.restaurantes.infrastructure.entity.EntidadPlato;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IPlatoEntityMapper {

    @Mapping(target = "id", source = "domain.id")
    @Mapping(target = "nombre", source = "domain.nombre.valor")
    @Mapping(target = "precio", source = "domain.precio.valor")
    @Mapping(target = "descripcion", source = "domain.descripcion.valor")
    @Mapping(target = "urlImagen", source = "domain.urlImagen.valor")
    @Mapping(target = "categoria", source = "domain.categoria.valor")
    @Mapping(target = "fechaCreacion", ignore = true)
    EntidadPlato toEntity(Plato domain);

    default Plato toDomain(EntidadPlato entity) {
        if (entity == null) return null;
        return new Plato(
                entity.getId(),
                new NombrePlato(entity.getNombre()),
                new PrecioPlato(entity.getPrecio()),
                new DescripcionPlato(entity.getDescripcion()),
                new UrlImagen(entity.getUrlImagen()),
                new CategoriaPlato(entity.getCategoria()),
                entity.isActivo(),
                entity.getIdRestaurante()
        );
    }
}
