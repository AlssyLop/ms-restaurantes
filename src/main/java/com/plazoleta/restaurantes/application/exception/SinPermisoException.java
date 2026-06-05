package com.plazoleta.restaurantes.application.exception;

public class SinPermisoException extends RuntimeException {
    public SinPermisoException(String message) {
        super(message);
    }
}
