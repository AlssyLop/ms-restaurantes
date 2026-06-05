package com.plazoleta.restaurantes.application.handle;

import com.plazoleta.restaurantes.application.dto.response.GestionarPlatoResponse;
import com.plazoleta.restaurantes.dominio.api.HabilitarDeshabilitarPlatoPort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GestionarPlatoHandle {

    private final HabilitarDeshabilitarPlatoPort gestionarPlatoPort;

    public GestionarPlatoHandle(HabilitarDeshabilitarPlatoPort gestionarPlatoPort) {
        this.gestionarPlatoPort = gestionarPlatoPort;
    }

    public GestionarPlatoResponse habilitar(Long idPlato) {
        Long idPropietario = (Long) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        gestionarPlatoPort.habilitar(idPlato, idPropietario);
        return new GestionarPlatoResponse("Plato habilitado exitosamente");
    }

    public GestionarPlatoResponse deshabilitar(Long idPlato) {
        Long idPropietario = (Long) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        gestionarPlatoPort.deshabilitar(idPlato, idPropietario);
        return new GestionarPlatoResponse("Plato deshabilitado exitosamente");
    }
}
