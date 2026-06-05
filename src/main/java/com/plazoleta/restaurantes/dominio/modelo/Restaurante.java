package com.plazoleta.restaurantes.dominio.modelo;

import com.plazoleta.restaurantes.dominio.modelo.value.NombreRestaurante;
import com.plazoleta.restaurantes.dominio.modelo.value.Nit;
import com.plazoleta.restaurantes.dominio.modelo.value.Telefono;
import com.plazoleta.restaurantes.dominio.modelo.value.UrlLogo;

public class Restaurante {
    private Long id;
    private NombreRestaurante nombre;
    private Nit nit;
    private String direccion;
    private Telefono telefono;
    private UrlLogo urlLogo;
    private Long idPropietario;
    private boolean activo;

    public Restaurante() {}

    public Restaurante(Long id, NombreRestaurante nombre, Nit nit, String direccion,
                       Telefono telefono, UrlLogo urlLogo, Long idPropietario, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.urlLogo = urlLogo;
        this.idPropietario = idPropietario;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NombreRestaurante getNombre() {
        return nombre;
    }

    public void setNombre(NombreRestaurante nombre) {
        this.nombre = nombre;
    }

    public Nit getNit() {
        return nit;
    }

    public void setNit(Nit nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Telefono getTelefono() {
        return telefono;
    }

    public void setTelefono(Telefono telefono) {
        this.telefono = telefono;
    }

    public UrlLogo getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(UrlLogo urlLogo) {
        this.urlLogo = urlLogo;
    }

    public Long getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(Long idPropietario) {
        this.idPropietario = idPropietario;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
