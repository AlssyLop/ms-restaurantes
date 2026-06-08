package com.plazoleta.restaurantes.application.handle;

import com.plazoleta.restaurantes.application.dto.request.CrearPlatoRequest;
import com.plazoleta.restaurantes.application.dto.response.CrearPlatoResponse;
import com.plazoleta.restaurantes.application.factory.PlatoFactory;
import com.plazoleta.restaurantes.dominio.api.CrearPlatoPort;
import com.plazoleta.restaurantes.dominio.modelo.Plato;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CrearPlatoHandle {

    private final CrearPlatoPort crearPlatoPort;
    private final PlatoFactory platoFactory;

    public CrearPlatoHandle(CrearPlatoPort crearPlatoPort, PlatoFactory platoFactory) {
        this.crearPlatoPort = crearPlatoPort;
        this.platoFactory = platoFactory;
    }

    public CrearPlatoResponse crearPlato(CrearPlatoRequest request, Long idPropietario) {
        Plato plato = platoFactory.toDomain(request);
        crearPlatoPort.crearPlato(plato, idPropietario);
        return new CrearPlatoResponse("Plato creado exitosamente");
    }
}
