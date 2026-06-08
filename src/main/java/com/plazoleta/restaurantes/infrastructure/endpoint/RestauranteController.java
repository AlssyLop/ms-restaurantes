package com.plazoleta.restaurantes.infrastructure.endpoint;

import com.plazoleta.restaurantes.application.dto.request.RestaurantePost;
import com.plazoleta.restaurantes.application.dto.response.PlatoPageResponse;
import com.plazoleta.restaurantes.application.dto.response.RestauranteCreado;
import com.plazoleta.restaurantes.application.dto.response.RestauranteInfoResponse;
import com.plazoleta.restaurantes.application.dto.response.RestaurantePageResponse;
import com.plazoleta.restaurantes.application.exception.ErrorResponse;
import com.plazoleta.restaurantes.application.handle.ConsultarRestauranteHandle;
import com.plazoleta.restaurantes.application.handle.ListarPlatosRestauranteHandle;
import com.plazoleta.restaurantes.application.handle.ListarRestaurantesHandle;
import com.plazoleta.restaurantes.application.handle.RestauranteHandle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurantes")
@Tag(name = "Restaurantes", description = "Gestion de restaurantes del sistema")
public class RestauranteController {

    private final RestauranteHandle restauranteHandle;
    private final ListarRestaurantesHandle listarRestaurantesHandle;
    private final ListarPlatosRestauranteHandle listarPlatosRestauranteHandle;
    private final ConsultarRestauranteHandle consultarRestauranteHandle;

    public RestauranteController(RestauranteHandle restauranteHandle,
                                 ListarRestaurantesHandle listarRestaurantesHandle,
                                 ListarPlatosRestauranteHandle listarPlatosRestauranteHandle,
                                 ConsultarRestauranteHandle consultarRestauranteHandle) {
        this.restauranteHandle = restauranteHandle;
        this.listarRestaurantesHandle = listarRestaurantesHandle;
        this.listarPlatosRestauranteHandle = listarPlatosRestauranteHandle;
        this.consultarRestauranteHandle = consultarRestauranteHandle;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Operation(summary = "Crear restaurante",
            description = "Crea un restaurante. Requiere autenticacion como ADMINISTRADOR.")
    @ApiResponse(responseCode = "201", description = "Restaurante creado exitosamente",
            content = @Content(schema = @Schema(implementation = RestauranteCreado.class)))
    @ApiResponse(responseCode = "400", description = "Error de validacion",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Propietario no encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "409", description = "Conflicto (nombre o NIT duplicado)",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<RestauranteCreado> crearRestaurante(
            @Valid @RequestBody RestaurantePost request) {
        RestauranteCreado response = restauranteHandle.crearRestaurante(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(summary = "Listar restaurantes",
            description = "Lista los restaurantes disponibles en orden alfabetico. Requiere autenticacion como CLIENTE.")
    @ApiResponse(responseCode = "200", description = "Listado de restaurantes paginado",
            content = @Content(schema = @Schema(implementation = RestaurantePageResponse.class)))
    public ResponseEntity<RestaurantePageResponse> listarRestaurantes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        RestaurantePageResponse response = listarRestaurantesHandle.listarRestaurantes(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{idRestaurante}/platos")
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(summary = "Listar platos de un restaurante",
            description = "Lista los platos activos de un restaurante, con paginacion y filtro opcional por categoria.")
    @ApiResponse(responseCode = "200", description = "Listado de platos paginado",
            content = @Content(schema = @Schema(implementation = PlatoPageResponse.class)))
    @ApiResponse(responseCode = "404", description = "Restaurante no encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<PlatoPageResponse> listarPlatosRestaurante(
            @PathVariable Long idRestaurante,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String categoria) {
        PlatoPageResponse response = listarPlatosRestauranteHandle.listarPlatos(
                idRestaurante, categoria, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(summary = "Obtener restaurante por ID",
            description = "Retorna la informacion basica de un restaurante. Usado internamente por ms-pedidos al crear un pedido.")
    @ApiResponse(responseCode = "200", description = "Restaurante encontrado",
            content = @Content(schema = @Schema(implementation = RestauranteInfoResponse.class)))
    @ApiResponse(responseCode = "404", description = "Restaurante no encontrado")
    public ResponseEntity<RestauranteInfoResponse> obtenerRestaurantePorId(@PathVariable Long id) {
        return consultarRestauranteHandle.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/propietario/{idPropietario}")
    @PreAuthorize("hasRole('PROPIETARIO')")
    @Operation(summary = "Obtener restaurante por propietario",
            description = "Retorna el restaurante asociado al propietario autenticado. Usado por ms-pedidos.")
    @ApiResponse(responseCode = "200", description = "Restaurante encontrado",
            content = @Content(schema = @Schema(implementation = RestauranteInfoResponse.class)))
    @ApiResponse(responseCode = "403", description = "El propietario autenticado no corresponde al ID solicitado")
    @ApiResponse(responseCode = "404", description = "Restaurante no encontrado para el propietario",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<RestauranteInfoResponse> obtenerRestaurantePorPropietario(
            @PathVariable Long idPropietario,
            Authentication authentication) {
        Long idUsuarioAutenticado = (Long) authentication.getPrincipal();
        if (!idUsuarioAutenticado.equals(idPropietario)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return consultarRestauranteHandle.obtenerPorPropietario(idPropietario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
