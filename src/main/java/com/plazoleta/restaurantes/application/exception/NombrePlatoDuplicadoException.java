package com.plazoleta.restaurantes.application.exception;

public class NombrePlatoDuplicadoException extends RuntimeException {
    public NombrePlatoDuplicadoException(String message) {
        super(message);
    }
}
