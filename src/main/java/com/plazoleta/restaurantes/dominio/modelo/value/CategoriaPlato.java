package com.plazoleta.restaurantes.dominio.modelo.value;

public class CategoriaPlato {
    private final String valor;

    public CategoriaPlato(String valor) {
        this.valor = normalizar(valor);
        this.validar();
    }

    private String normalizar(String value) {
        if (value == null) return null;
        return value.trim().replaceAll("\\s+", " ");
    }

    private void validar() {
        if (valor == null || valor.isEmpty()) {
            throw new IllegalArgumentException("La categoria del plato es requerida");
        }
    }

    public String getValor() {
        return valor;
    }
}
