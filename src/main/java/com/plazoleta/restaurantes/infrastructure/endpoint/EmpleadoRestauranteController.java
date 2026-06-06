package com.plazoleta.restaurantes.infrastructure.endpoint;

import com.plazoleta.restaurantes.application.dto.request.AsociarEmpleadoRequest;
import com.plazoleta.restaurantes.application.dto.response.AsociarEmpleadoResponse;
import com.plazoleta.restaurantes.application.dto.response.EmpleadoRestauranteResponse;
import com.plazoleta.restaurantes.application.exception.ErrorResponse;
import com.plazoleta.restaurantes.application.handle.EmpleadoRestauranteHandle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurantes")
@Tag(name = "Empleados del Restaurante", description = "Gestion de empleados asociados a un restaurante")
public class EmpleadoRestauranteController {

    private final EmpleadoRestauranteHandle empleadoRestauranteHandle;

    public EmpleadoRestauranteController(EmpleadoRestauranteHandle empleadoRestauranteHandle) {
        this.empleadoRestauranteHandle = empleadoRestauranteHandle;
    }

    @PostMapping("/empleados")
    @PreAuthorize("hasRole('PROPIETARIO')")
    @Operation(summary = "Asociar empleado a restaurante",
            description = "Asocia un empleado al restaurante del propietario autenticado. Requiere rol PROPIETARIO.")
    @ApiResponse(responseCode = "201", description = "Empleado asociado exitosamente",
            content = @Content(schema = @Schema(implementation = AsociarEmpleadoResponse.class)))
    @ApiResponse(responseCode = "400", description = "Error de validacion",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "401", description = "No autorizado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Restaurante no encontrado para el propietario",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<AsociarEmpleadoResponse> asociarEmpleado(
            @RequestBody AsociarEmpleadoRequest request) {
        AsociarEmpleadoResponse response = empleadoRestauranteHandle.asociar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/empleado/{idEmpleado}")
    @Operation(summary = "Obtener restaurante del empleado",
            description = "Retorna el restaurante al que pertenece un empleado. Usado por ms-pedidos para filtrar pedidos.")
    @ApiResponse(responseCode = "200", description = "Empleado encontrado",
            content = @Content(schema = @Schema(implementation = EmpleadoRestauranteResponse.class)))
    @ApiResponse(responseCode = "404", description = "Empleado no asociado a ningun restaurante")
    public ResponseEntity<EmpleadoRestauranteResponse> obtenerRestauranteEmpleado(
            @PathVariable Long idEmpleado) {
        EmpleadoRestauranteResponse response = empleadoRestauranteHandle.obtenerRestauranteDelEmpleado(idEmpleado);
        return ResponseEntity.ok(response);
    }
}