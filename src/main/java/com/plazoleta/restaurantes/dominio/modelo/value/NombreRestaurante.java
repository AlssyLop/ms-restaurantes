package com.plazoleta.restaurantes.dominio.modelo.value;

import java.util.regex.Pattern;

public class NombreRestaurante {
    private final String valor;
    private static final Pattern SOLO_NUMEROS = Pattern.compile("^[0-9]+$");

    public NombreRestaurante(String valor) {
        this.valor = valor;
        this.validar();
    }

    private void validar() {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del restaurante es requerido");
        }
        if (SOLO_NUMEROS.matcher(valor.trim()).matches()) {
            throw new IllegalArgumentException("El nombre del restaurante no puede contener solo numeros");
        }
    }

    public String getValor() {
        return valor;
    }
}
