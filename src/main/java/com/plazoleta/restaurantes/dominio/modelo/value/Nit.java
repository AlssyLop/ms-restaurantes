package com.plazoleta.restaurantes.dominio.modelo.value;

import java.util.regex.Pattern;

public class Nit {
    private final String valor;
    private static final Pattern DIGITOS = Pattern.compile("^[0-9]+$");

    public Nit(String valor) {
        this.valor = valor;
        this.validar();
    }

    private void validar() {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El NIT es requerido");
        }
        if (!DIGITOS.matcher(valor.trim()).matches()) {
            throw new IllegalArgumentException("El NIT debe ser numerico");
        }
    }

    public String getValor() {
        return valor;
    }
}
