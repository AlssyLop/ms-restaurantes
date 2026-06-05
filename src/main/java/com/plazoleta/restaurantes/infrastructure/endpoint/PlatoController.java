package com.plazoleta.restaurantes.infrastructure.endpoint;

import com.plazoleta.restaurantes.application.dto.request.CrearPlatoRequest;
import com.plazoleta.restaurantes.application.dto.request.ModificarPlatoRequest;
import com.plazoleta.restaurantes.application.dto.response.CrearPlatoResponse;
import com.plazoleta.restaurantes.application.dto.response.GestionarPlatoResponse;
import com.plazoleta.restaurantes.application.dto.response.ModificarPlatoResponse;
import com.plazoleta.restaurantes.application.handle.CrearPlatoHandle;
import com.plazoleta.restaurantes.application.handle.GestionarPlatoHandle;
import com.plazoleta.restaurantes.application.handle.ModificarPlatoHandle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/platos")
@Tag(name = "Platos", description = "Gestion de platos del sistema")
public class PlatoController {

    private final CrearPlatoHandle crearPlatoHandle;
    private final ModificarPlatoHandle modificarPlatoHandle;
    private final GestionarPlatoHandle gestionarPlatoHandle;

    public PlatoController(CrearPlatoHandle crearPlatoHandle,
                           ModificarPlatoHandle modificarPlatoHandle,
                           GestionarPlatoHandle gestionarPlatoHandle) {
        this.crearPlatoHandle = crearPlatoHandle;
        this.modificarPlatoHandle = modificarPlatoHandle;
        this.gestionarPlatoHandle = gestionarPlatoHandle;
    }

    @PostMapping
    @PreAuthorize("hasRole('PROPIETARIO')")
    @Operation(summary = "Crear plato",
            description = "Crea un plato asociado al restaurante del propietario autenticado.")
    @ApiResponse(responseCode = "201", description = "Plato creado exitosamente",
            content = @Content(schema = @Schema(implementation = CrearPlatoResponse.class)))
    @ApiResponse(responseCode = "400", description = "Error de validacion")
    @ApiResponse(responseCode = "404", description = "Propietario no encontrado o sin restaurante")
    @ApiResponse(responseCode = "409", description = "Conflicto (nombre de plato duplicado en el restaurante)")
    public ResponseEntity<CrearPlatoResponse> crearPlato(@RequestBody CrearPlatoRequest request) {
        CrearPlatoResponse response = crearPlatoHandle.crearPlato(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{idPlato}")
    @PreAuthorize("hasRole('PROPIETARIO')")
    @Operation(summary = "Modificar plato",
            description = "Modifica precio y/o descripcion de un plato del propietario autenticado.")
    @ApiResponse(responseCode = "200", description = "Plato modificado exitosamente",
            content = @Content(schema = @Schema(implementation = ModificarPlatoResponse.class)))
    @ApiResponse(responseCode = "400", description = "Error de validacion")
    @ApiResponse(responseCode = "403", description = "No autorizado para modificar este plato")
    @ApiResponse(responseCode = "404", description = "Plato o propietario no encontrado")
    public ResponseEntity<ModificarPlatoResponse> modificarPlato(
            @PathVariable Long idPlato, @RequestBody ModificarPlatoRequest request) {
        ModificarPlatoResponse response = modificarPlatoHandle.modificarPlato(idPlato, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{idPlato}/habilitar")
    @PreAuthorize("hasRole('PROPIETARIO')")
    @Operation(summary = "Habilitar plato",
            description = "Habilita un plato del restaurante del propietario autenticado.")
    @ApiResponse(responseCode = "200", description = "Plato habilitado exitosamente",
            content = @Content(schema = @Schema(implementation = GestionarPlatoResponse.class)))
    @ApiResponse(responseCode = "400", description = "El plato ya se encuentra habilitado")
    @ApiResponse(responseCode = "403", description = "No autorizado para modificar este plato")
    @ApiResponse(responseCode = "404", description = "Plato o propietario no encontrado")
    public ResponseEntity<GestionarPlatoResponse> habilitarPlato(@PathVariable Long idPlato) {
        GestionarPlatoResponse response = gestionarPlatoHandle.habilitar(idPlato);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{idPlato}/deshabilitar")
    @PreAuthorize("hasRole('PROPIETARIO')")
    @Operation(summary = "Deshabilitar plato",
            description = "Deshabilita un plato del restaurante del propietario autenticado.")
    @ApiResponse(responseCode = "200", description = "Plato deshabilitado exitosamente",
            content = @Content(schema = @Schema(implementation = GestionarPlatoResponse.class)))
    @ApiResponse(responseCode = "400", description = "El plato ya se encuentra deshabilitado")
    @ApiResponse(responseCode = "403", description = "No autorizado para modificar este plato")
    @ApiResponse(responseCode = "404", description = "Plato o propietario no encontrado")
    public ResponseEntity<GestionarPlatoResponse> deshabilitarPlato(@PathVariable Long idPlato) {
        GestionarPlatoResponse response = gestionarPlatoHandle.deshabilitar(idPlato);
        return ResponseEntity.ok(response);
    }
}
