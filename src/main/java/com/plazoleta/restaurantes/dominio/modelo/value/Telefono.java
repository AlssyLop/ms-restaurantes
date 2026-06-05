package com.plazoleta.restaurantes.dominio.modelo.value;

import java.util.regex.Pattern;

public class Telefono {
    private final String valor;
    private static final Pattern TELEFONO = Pattern.compile("^\\+[0-9]+$");

    public Telefono(String valor) {
        this.valor = valor;
        this.validar();
    }

    private void validar() {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El telefono es requerido");
        }
        String tel = valor.trim();
        if (tel.length() > 13 || !TELEFONO.matcher(tel).matches()) {
            throw new IllegalArgumentException("El telefono debe comenzar con + y tener maximo 13 caracteres");
        }
    }

    public String getValor() {
        return valor;
    }
}
