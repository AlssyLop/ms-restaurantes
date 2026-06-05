package com.plazoleta.restaurantes.dominio.modelo;

public class UsuarioRestaurante {
    private final Long id;
    private final String rol;

    public UsuarioRestaurante(Long id, String rol) {
        this.id = id;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public String getRol() {
        return rol;
    }
}
