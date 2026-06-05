package com.plazoleta.restaurantes.dominio.modelo;

import com.plazoleta.restaurantes.dominio.modelo.value.CategoriaPlato;
import com.plazoleta.restaurantes.dominio.modelo.value.DescripcionPlato;
import com.plazoleta.restaurantes.dominio.modelo.value.NombrePlato;
import com.plazoleta.restaurantes.dominio.modelo.value.PrecioPlato;
import com.plazoleta.restaurantes.dominio.modelo.value.UrlImagen;

public class Plato {
    private Long id;
    private NombrePlato nombre;
    private PrecioPlato precio;
    private DescripcionPlato descripcion;
    private UrlImagen urlImagen;
    private CategoriaPlato categoria;
    private boolean activo;
    private Long idRestaurante;

    public Plato() {}

    public Plato(Long id, NombrePlato nombre, PrecioPlato precio, DescripcionPlato descripcion,
                 UrlImagen urlImagen, CategoriaPlato categoria, boolean activo, Long idRestaurante) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.urlImagen = urlImagen;
        this.categoria = categoria;
        this.activo = activo;
        this.idRestaurante = idRestaurante;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public Long getIdRestaurante() { return idRestaurante; }
    public void setIdRestaurante(Long idRestaurante) { this.idRestaurante = idRestaurante; }
}
