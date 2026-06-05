package com.plazoleta.restaurantes.dominio.modelo.value;

public class DescripcionPlato {
    private final String valor;

    public DescripcionPlato(String valor) {
        this.valor = normalizar(valor);
        this.validar();
    }

    private String normalizar(String value) {
        if (value == null) return null;
        return value.trim().replaceAll("\\s+", " ");
    }

    private void validar() {
        if (valor == null || valor.isEmpty()) {
            throw new IllegalArgumentException("La descripcion del plato es requerida");
        }
    }

    public String getValor() {
        return valor;
    }
}
