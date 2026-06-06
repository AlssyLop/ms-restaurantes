package com.plazoleta.restaurantes.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class RestaurantePost {

    @Schema(description = "Nombre del restaurante (no puede ser solo numeros)", example = "La Casa de la Pasta")
    private String nombre;

    @Schema(description = "NIT del restaurante (solo numerico)", example = "123456789")
    private String nit;

    @Schema(description = "Direccion del restaurante", example = "Calle 123 #45-67")
    private String direccion;

    @Schema(description = "Telefono con formato internacional", example = "+573001234567")
    private String telefono;

    @Schema(description = "URL del logo del restaurante", example = "https://example.com/logo.png")
    private String urlLogo;

    @Schema(description = "ID del propietario", example = "1")
    private Long idPropietario;

    public RestaurantePost() {}

    public RestaurantePost(String nombre, String nit, String direccion,
                           String telefono, String urlLogo, Long idPropietario) {
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.urlLogo = urlLogo;
        this.idPropietario = idPropietario;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getUrlLogo() { return urlLogo; }
    public void setUrlLogo(String urlLogo) { this.urlLogo = urlLogo; }

    public Long getIdPropietario() { return idPropietario; }
    public void setIdPropietario(Long idPropietario) { this.idPropietario = idPropietario; }
}
