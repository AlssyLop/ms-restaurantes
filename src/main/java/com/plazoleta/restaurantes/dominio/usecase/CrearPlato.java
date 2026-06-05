package com.plazoleta.restaurantes.dominio.usecase;

import com.plazoleta.restaurantes.application.exception.NombrePlatoDuplicadoException;
import com.plazoleta.restaurantes.application.exception.PropietarioNoEncontradoException;
import com.plazoleta.restaurantes.dominio.api.CrearPlatoPort;
import com.plazoleta.restaurantes.dominio.modelo.Plato;
import com.plazoleta.restaurantes.dominio.modelo.Restaurante;
import com.plazoleta.restaurantes.dominio.modelo.UsuarioRestaurante;
import com.plazoleta.restaurantes.dominio.modelo.value.RolPropietario;
import com.plazoleta.restaurantes.dominio.spi.PlatoRepositoryPort;
import com.plazoleta.restaurantes.dominio.spi.RestauranteRepositoryPort;
import com.plazoleta.restaurantes.dominio.spi.UsuarioValidacionPort;

public class CrearPlato implements CrearPlatoPort {

    private final PlatoRepositoryPort platoRepository;
    private final RestauranteRepositoryPort restauranteRepository;
    private final UsuarioValidacionPort usuarioValidacion;

    public CrearPlato(PlatoRepositoryPort platoRepository,
                      RestauranteRepositoryPort restauranteRepository,
                      UsuarioValidacionPort usuarioValidacion) {
        this.platoRepository = platoRepository;
        this.restauranteRepository = restauranteRepository;
        this.usuarioValidacion = usuarioValidacion;
    }

    @Override
    public Plato crearPlato(Plato plato, Long idPropietario) {
        validarPropietario(idPropietario);
        Restaurante restaurante = restauranteRepository.findByIdPropietario(idPropietario)
                .orElseThrow(() -> new PropietarioNoEncontradoException(
                        "El restaurante del usuario con id " + idPropietario + " no existe"));
        validarNombreUnico(plato, restaurante.getId());
        plato.setIdRestaurante(restaurante.getId());
        return platoRepository.save(plato);
    }

    private void validarPropietario(Long idPropietario) {
        UsuarioRestaurante usuario = usuarioValidacion.consultarPorId(idPropietario)
                .orElseThrow(() -> new PropietarioNoEncontradoException(
                        "El usuario con id " + idPropietario + " no existe"));
        new RolPropietario(usuario.getRol());
    }

    private void validarNombreUnico(Plato plato, Long idRestaurante) {
        if (platoRepository.existsByNombreAndIdRestaurante(plato.getNombre(), idRestaurante)) {
            throw new NombrePlatoDuplicadoException("El plato " + plato.getNombre().getValor()
                    + " ya existe en el restaurante");
        }
    }
}
