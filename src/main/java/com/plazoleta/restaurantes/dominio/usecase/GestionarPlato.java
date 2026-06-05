package com.plazoleta.restaurantes.dominio.usecase;

import com.plazoleta.restaurantes.application.exception.PlatoNoEncontradoException;
import com.plazoleta.restaurantes.application.exception.PlatoYaEnEseEstadoException;
import com.plazoleta.restaurantes.application.exception.PropietarioNoEncontradoException;
import com.plazoleta.restaurantes.application.exception.SinPermisoException;
import com.plazoleta.restaurantes.dominio.api.HabilitarDeshabilitarPlatoPort;
import com.plazoleta.restaurantes.dominio.modelo.Plato;
import com.plazoleta.restaurantes.dominio.modelo.Restaurante;
import com.plazoleta.restaurantes.dominio.modelo.UsuarioRestaurante;
import com.plazoleta.restaurantes.dominio.modelo.value.RolPropietario;
import com.plazoleta.restaurantes.dominio.spi.PlatoRepositoryPort;
import com.plazoleta.restaurantes.dominio.spi.RestauranteRepositoryPort;
import com.plazoleta.restaurantes.dominio.spi.UsuarioValidacionPort;

public class GestionarPlato implements HabilitarDeshabilitarPlatoPort {

    private final PlatoRepositoryPort platoRepository;
    private final RestauranteRepositoryPort restauranteRepository;
    private final UsuarioValidacionPort usuarioValidacion;

    public GestionarPlato(PlatoRepositoryPort platoRepository,
                          RestauranteRepositoryPort restauranteRepository,
                          UsuarioValidacionPort usuarioValidacion) {
        this.platoRepository = platoRepository;
        this.restauranteRepository = restauranteRepository;
        this.usuarioValidacion = usuarioValidacion;
    }

    @Override
    public Plato habilitar(Long idPlato, Long idPropietario) {
        validarPropietario(idPropietario);

        Plato plato = platoRepository.findById(idPlato)
                .orElseThrow(() -> new PlatoNoEncontradoException(
                        "El plato con id " + idPlato + " no existe"));

        Restaurante restaurante = restauranteRepository.findByIdPropietario(idPropietario)
                .orElseThrow(() -> new PropietarioNoEncontradoException(
                        "El restaurante del usuario con id " + idPropietario + " no existe"));

        if (!plato.getIdRestaurante().equals(restaurante.getId())) {
            throw new SinPermisoException("No tienes permiso para modificar este plato");
        }

        if (plato.isActivo()) {
            throw new PlatoYaEnEseEstadoException("El plato ya se encuentra habilitado");
        }

        plato.setActivo(true);
        return platoRepository.save(plato);
    }

    @Override
    public Plato deshabilitar(Long idPlato, Long idPropietario) {
        validarPropietario(idPropietario);

        Plato plato = platoRepository.findById(idPlato)
                .orElseThrow(() -> new PlatoNoEncontradoException(
                        "El plato con id " + idPlato + " no existe"));

        Restaurante restaurante = restauranteRepository.findByIdPropietario(idPropietario)
                .orElseThrow(() -> new PropietarioNoEncontradoException(
                        "El restaurante del usuario con id " + idPropietario + " no existe"));

        if (!plato.getIdRestaurante().equals(restaurante.getId())) {
            throw new SinPermisoException("No tienes permiso para modificar este plato");
        }

        if (!plato.isActivo()) {
            throw new PlatoYaEnEseEstadoException("El plato ya se encuentra deshabilitado");
        }

        plato.setActivo(false);
        return platoRepository.save(plato);
    }

    private void validarPropietario(Long idPropietario) {
        UsuarioRestaurante usuario = usuarioValidacion.consultarPorId(idPropietario)
                .orElseThrow(() -> new PropietarioNoEncontradoException(
                        "El usuario con id " + idPropietario + " no existe"));
        new RolPropietario(usuario.getRol());
    }
}
