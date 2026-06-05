package com.plazoleta.restaurantes.application.handle;

import com.plazoleta.restaurantes.application.dto.request.ModificarPlatoRequest;
import com.plazoleta.restaurantes.application.dto.response.ModificarPlatoResponse;
import com.plazoleta.restaurantes.dominio.api.ModificarPlatoPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ModificarPlatoHandle {

    private final ModificarPlatoPort modificarPlatoPort;

    public ModificarPlatoHandle(ModificarPlatoPort modificarPlatoPort) {
        this.modificarPlatoPort = modificarPlatoPort;
    }

    public ModificarPlatoResponse modificarPlato(Long idPlato, ModificarPlatoRequest request) {
        modificarPlatoPort.modificarPlato(idPlato, request.getPrecio(),
                request.getDescripcion(), request.getIdPropietario());
        return new ModificarPlatoResponse("Plato modificado exitosamente");
    }
}
