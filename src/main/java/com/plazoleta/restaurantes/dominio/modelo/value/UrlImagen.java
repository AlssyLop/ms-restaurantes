package com.plazoleta.restaurantes.dominio.modelo.value;

import java.util.regex.Pattern;

public class UrlImagen {
    private final String valor;
    private static final Pattern URL = Pattern.compile("^(http|https)://.+$");

    public UrlImagen(String valor) {
        this.valor = normalizar(valor);
        this.validar();
    }

    private String normalizar(String value) {
        if (value == null) return null;
        return value.trim().replaceAll("\\s+", " ");
    }

    private void validar() {
        if (valor == null || valor.isEmpty()) {
            throw new IllegalArgumentException("La URL de la imagen es requerida");
        }
        if (!URL.matcher(valor).matches()) {
            throw new IllegalArgumentException("La URL de la imagen no tiene un formato valido");
        }
    }

    public String getValor() {
        return valor;
    }
}
