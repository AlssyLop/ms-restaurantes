package com.plazoleta.restaurantes.dominio.modelo.value;

public class NombrePlato {
    private final String valor;

    public NombrePlato(String valor) {
        this.valor = normalizar(valor);
        this.validar();
    }

    private String normalizar(String value) {
        if (value == null) return null;
        return value.trim().replaceAll("\\s+", " ");
    }

    private void validar() {
        if (valor == null || valor.isEmpty()) {
            throw new IllegalArgumentException("El nombre del plato es requerido");
        }
    }

    public String getValor() {
        return valor;
    }
}
