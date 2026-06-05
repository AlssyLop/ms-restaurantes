package com.plazoleta.restaurantes.dominio.modelo.value;

import java.util.regex.Pattern;

public class UrlLogo {
    private final String valor;
    private static final Pattern URL = Pattern.compile("^(http|https)://.+$");

    public UrlLogo(String valor) {
        this.valor = valor;
        this.validar();
    }

    private void validar() {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("La URL del logo es requerida");
        }
        if (!URL.matcher(valor.trim()).matches()) {
            throw new IllegalArgumentException("La URL del logo debe tener un formato valido");
        }
    }

    public String getValor() {
        return valor;
    }
}
