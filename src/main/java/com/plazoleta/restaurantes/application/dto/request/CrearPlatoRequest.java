package com.plazoleta.restaurantes.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class CrearPlatoRequest {

    @Schema(description = "Nombre del plato", example = "Pasta Carbonara")
    private String nombre;

    @Schema(description = "Precio del plato (entero positivo)", example = "25000")
    private Integer precio;

    @Schema(description = "Descripcion del plato", example = "Pasta con crema, huevo y panceta")
    private String descripcion;

    @Schema(description = "URL de la imagen del plato", example = "https://example.com/plato.png")
    private String urlImagen;

    @Schema(description = "Categoria del plato", example = "Italiana")
    private String categoria;

    @Schema(description = "ID del propietario", example = "1")
    private Long idPropietario;

    public CrearPlatoRequest() {}

    public CrearPlatoRequest(String nombre, Integer precio, String descripcion,
                              String urlImagen, String categoria, Long idPropietario) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.urlImagen = urlImagen;
        this.categoria = categoria;
        this.idPropietario = idPropietario;
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

    public Long getIdPropietario() { return idPropietario; }
    public void setIdPropietario(Long idPropietario) { this.idPropietario = idPropietario; }
}
