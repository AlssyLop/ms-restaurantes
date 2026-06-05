package com.plazoleta.restaurantes.application.handle;

import com.plazoleta.restaurantes.application.dto.request.AsociarEmpleadoRequest;
import com.plazoleta.restaurantes.application.dto.response.AsociarEmpleadoResponse;
import com.plazoleta.restaurantes.application.dto.response.EmpleadoRestauranteResponse;
import com.plazoleta.restaurantes.application.factory.EmpleadoRestauranteFactory;
import com.plazoleta.restaurantes.dominio.api.AsociarEmpleadoPort;
import com.plazoleta.restaurantes.dominio.modelo.EmpleadoRestaurante;
import com.plazoleta.restaurantes.dominio.modelo.Restaurante;
import com.plazoleta.restaurantes.dominio.spi.EmpleadoRestauranteRepositoryPort;
import com.plazoleta.restaurantes.dominio.spi.RestauranteRepositoryPort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmpleadoRestauranteHandle {

    private final AsociarEmpleadoPort asociarEmpleado;
    private final EmpleadoRestauranteFactory factory;
    private final RestauranteRepositoryPort restauranteRepository;
    private final EmpleadoRestauranteRepositoryPort empleadoRestauranteRepository;

    public EmpleadoRestauranteHandle(AsociarEmpleadoPort asociarEmpleado,
                                     EmpleadoRestauranteFactory factory,
                                     RestauranteRepositoryPort restauranteRepository,
                                     EmpleadoRestauranteRepositoryPort empleadoRestauranteRepository) {
        this.asociarEmpleado = asociarEmpleado;
        this.factory = factory;
        this.restauranteRepository = restauranteRepository;
        this.empleadoRestauranteRepository = empleadoRestauranteRepository;
    }

    public AsociarEmpleadoResponse asociar(AsociarEmpleadoRequest request) {
        Long idPropietario = (Long) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        Restaurante restaurante = restauranteRepository.findByIdPropietario(idPropietario)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontro un restaurante para el propietario autenticado"));

        EmpleadoRestaurante domain = factory.toDomain(request, restaurante.getId());
        asociarEmpleado.asociar(domain);

        return new AsociarEmpleadoResponse("Empleado asociado exitosamente");
    }

    public EmpleadoRestauranteResponse obtenerRestauranteDelEmpleado(Long idEmpleado) {
        EmpleadoRestaurante emp = empleadoRestauranteRepository.findByIdEmpleado(idEmpleado)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Empleado no asociado a ningun restaurante"));
        return new EmpleadoRestauranteResponse(emp.getIdEmpleado(), emp.getIdRestaurante(), emp.getIdCargo());
    }
}