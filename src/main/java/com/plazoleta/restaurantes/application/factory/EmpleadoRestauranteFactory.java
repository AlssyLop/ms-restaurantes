package com.plazoleta.restaurantes.application.factory;

import com.plazoleta.restaurantes.application.dto.request.AsociarEmpleadoRequest;
import com.plazoleta.restaurantes.dominio.modelo.EmpleadoRestaurante;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoRestauranteFactory {

    public EmpleadoRestaurante toDomain(AsociarEmpleadoRequest request, Long idRestaurante) {
        EmpleadoRestaurante er = new EmpleadoRestaurante();
        er.setIdEmpleado(request.getIdEmpleado());
        er.setIdRestaurante(idRestaurante);
        er.setIdCargo(request.getIdCargo());
        er.setActivo(true);
        return er;
    }
}
