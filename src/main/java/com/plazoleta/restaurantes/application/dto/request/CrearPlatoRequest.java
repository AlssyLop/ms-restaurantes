package com.plazoleta.restaurantes.application.dto.request;

import com.plazoleta.restaurantes.dominio.modelo.value.CategoriaPlato;
import com.plazoleta.restaurantes.dominio.modelo.value.DescripcionPlato;
import com.plazoleta.restaurantes.dominio.modelo.value.NombrePlato;
import com.plazoleta.restaurantes.dominio.modelo.value.PrecioPlato;
import com.plazoleta.restaurantes.dominio.modelo.value.UrlImagen;

public class CrearPlatoRequest {

    private NombrePlato nombre;
    private PrecioPlato precio;
    private DescripcionPlato descripcion;
    private UrlImagen urlImagen;
    private CategoriaPlato categoria;
    private Long idPropietario;

    public CrearPlatoRequest() {}

    public CrearPlatoRequest(NombrePlato nombre, PrecioPlato precio, DescripcionPlato descripcion,
                             UrlImagen urlImagen, CategoriaPlato categoria, Long idPropietario) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.urlImagen = urlImagen;
        this.categoria = categoria;
        this.idPropietario = idPropietario;
    }

    public NombrePlato getNombre() { return nombre; }
    public void setNombre(NombrePlato nombre) { this.nombre = nombre; }

    public PrecioPlato getPrecio() { return precio; }
    public void setPrecio(PrecioPlato precio) { this.precio = precio; }

    public DescripcionPlato getDescripcion() { return descripcion; }
    public void setDescripcion(DescripcionPlato descripcion) { this.descripcion = descripcion; }

    public UrlImagen getUrlImagen() { return urlImagen; }
    public void setUrlImagen(UrlImagen urlImagen) { this.urlImagen = urlImagen; }

    public CategoriaPlato getCategoria() { return categoria; }
    public void setCategoria(CategoriaPlato categoria) { this.categoria = categoria; }

    public Long getIdPropietario() { return idPropietario; }
    public void setIdPropietario(Long idPropietario) { this.idPropietario = idPropietario; }
}
