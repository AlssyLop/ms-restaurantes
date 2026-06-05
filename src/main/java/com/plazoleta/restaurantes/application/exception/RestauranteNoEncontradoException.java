package com.plazoleta.restaurantes.application.exception;

public class RestauranteNoEncontradoException extends RuntimeException {
    public RestauranteNoEncontradoException(String message) {
        super(message);
    }
}
