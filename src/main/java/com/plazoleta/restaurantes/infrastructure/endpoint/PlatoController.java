package com.plazoleta.restaurantes.infrastructure.endpoint;

import com.plazoleta.restaurantes.application.dto.request.CrearPlatoRequest;
import com.plazoleta.restaurantes.application.handle.CrearPlatoHandle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/platos")
@Tag(name = "Platos", description = "Gestion de platos del sistema")
public class PlatoController {

    private final CrearPlatoHandle crearPlatoHandle;

    public PlatoController(CrearPlatoHandle crearPlatoHandle) {
        this.crearPlatoHandle = crearPlatoHandle;
    }

    @PostMapping
    @Operation(summary = "Crear plato",
            description = "Crea un plato asociado al restaurante del propietario autenticado.")
    @ApiResponse(responseCode = "201", description = "Plato creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Error de validacion")
    @ApiResponse(responseCode = "404", description = "Propietario no encontrado o sin restaurante")
    @ApiResponse(responseCode = "409", description = "Conflicto (nombre de plato duplicado en el restaurante)")
    public ResponseEntity<Void> crearPlato(@RequestBody CrearPlatoRequest request) {
        crearPlatoHandle.crearPlato(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
