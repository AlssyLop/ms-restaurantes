package com.plazoleta.restaurantes.application.dto.response;

public class PlatoListadoResponse {

    private String nombre;
    private Integer precio;
    private String descripcion;
    private String urlImagen;
    private String categoria;

    public PlatoListadoResponse() {}

    public PlatoListadoResponse(String nombre, Integer precio, String descripcion,
                                String urlImagen, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.urlImagen = urlImagen;
        this.categoria = categoria;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getPrecio() { return precio; }
    public void setPrecio(Integer precio) { this.precio = precio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getUrlImagen() { return urlImagen; }
    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
