package com.plazoleta.restaurantes.infrastructure.endpoint.handler;

import com.plazoleta.restaurantes.application.exception.ErrorResponse;
import com.plazoleta.restaurantes.application.exception.NombrePlatoDuplicadoException;
import com.plazoleta.restaurantes.application.exception.PlatoNoEncontradoException;
import com.plazoleta.restaurantes.application.exception.PlatoYaEnEseEstadoException;
import com.plazoleta.restaurantes.application.exception.PropietarioNoEncontradoException;
import com.plazoleta.restaurantes.application.exception.SinPermisoException;
import java.util.List;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errores = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getDefaultMessage())
                .toList();
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("Errores de validacion", errores));
    }

    @ExceptionHandler(PropietarioNoEncontradoException.class)
    public ResponseEntity<Void> handlePropietarioNoEncontrado(PropietarioNoEncontradoException ex) {
        if (ex.getMessage() != null && ex.getMessage().contains(" no existe")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NombrePlatoDuplicadoException.class)
    public ResponseEntity<Void> handleNombrePlatoDuplicado(NombrePlatoDuplicadoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(ex.getMessage(), null));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateKey(DuplicateKeyException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(ex.getMessage(), null));
    }

    @ExceptionHandler(PlatoNoEncontradoException.class)
    public ResponseEntity<Void> handlePlatoNoEncontrado(PlatoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(SinPermisoException.class)
    public ResponseEntity<Void> handleSinPermiso(SinPermisoException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(PlatoYaEnEseEstadoException.class)
    public ResponseEntity<ErrorResponse> handlePlatoYaEnEseEstado(PlatoYaEnEseEstadoException ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Error interno del servidor", null));
    }
}
