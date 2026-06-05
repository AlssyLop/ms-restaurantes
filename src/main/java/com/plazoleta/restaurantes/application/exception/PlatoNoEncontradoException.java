package com.plazoleta.restaurantes.application.exception;

public class PlatoNoEncontradoException extends RuntimeException {
    public PlatoNoEncontradoException(String message) {
        super(message);
    }
}
