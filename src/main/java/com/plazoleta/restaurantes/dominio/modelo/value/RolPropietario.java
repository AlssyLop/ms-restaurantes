package com.plazoleta.restaurantes.dominio.modelo.value;

public class RolPropietario {
    private final String valor;
    private static final String ROL_PROPIETARIO = "PROPIETARIO";

    public RolPropietario(String valor) {
        this.valor = valor;
        this.validar();
    }

    private void validar() {
        if (valor == null || !ROL_PROPIETARIO.equals(valor)) {
            throw new IllegalArgumentException("El rol debe ser PROPIETARIO");
        }
    }

    public String getValor() {
        return valor;
    }
}
