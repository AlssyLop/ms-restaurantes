package com.plazoleta.restaurantes.dominio.modelo.value;

public class PrecioPlato {
    private final Integer valor;

    public PrecioPlato(Integer valor) {
        this.valor = valor;
        this.validar();
    }

    private void validar() {
        if (valor == null || valor <= 0) {
            throw new IllegalArgumentException("El precio debe ser un numero entero positivo mayor a 0");
        }
    }

    public Integer getValor() {
        return valor;
    }
}
