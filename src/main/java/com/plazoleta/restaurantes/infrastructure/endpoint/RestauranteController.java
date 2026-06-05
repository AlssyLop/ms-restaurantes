package com.plazoleta.restaurantes.infrastructure.endpoint;

import com.plazoleta.restaurantes.application.dto.request.RestaurantePost;
import com.plazoleta.restaurantes.application.dto.response.RestauranteCreado;
import com.plazoleta.restaurantes.application.handle.RestauranteHandle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurantes")
@Tag(name = "Restaurantes", description = "Gestion de restaurantes del sistema")
public class RestauranteController {

    private final RestauranteHandle restauranteHandle;

    public RestauranteController(RestauranteHandle restauranteHandle) {
        this.restauranteHandle = restauranteHandle;
    }

    @PostMapping
    @Operation(summary = "Crear restaurante",
            description = "Crea un restaurante. Requiere autenticacion como ADMINISTRADOR.")
    @ApiResponse(responseCode = "201", description = "Restaurante creado exitosamente",
            content = @Content(schema = @Schema(implementation = RestauranteCreado.class)))
    @ApiResponse(responseCode = "400", description = "Error de validacion")
    @ApiResponse(responseCode = "404", description = "Propietario no encontrado")
    @ApiResponse(responseCode = "409", description = "Conflicto (nombre o NIT duplicado)")
    public ResponseEntity<RestauranteCreado> crearRestaurante(
            @Valid @RequestBody RestaurantePost request) {
        RestauranteCreado response = restauranteHandle.crearRestaurante(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
