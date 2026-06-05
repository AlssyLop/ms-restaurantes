package com.plazoleta.restaurantes.dominio.usecase;

import com.plazoleta.restaurantes.application.exception.PlatoNoEncontradoException;
import com.plazoleta.restaurantes.application.exception.PropietarioNoEncontradoException;
import com.plazoleta.restaurantes.application.exception.SinPermisoException;
import com.plazoleta.restaurantes.dominio.api.ModificarPlatoPort;
import com.plazoleta.restaurantes.dominio.modelo.Plato;
import com.plazoleta.restaurantes.dominio.modelo.Restaurante;
import com.plazoleta.restaurantes.dominio.modelo.UsuarioRestaurante;
import com.plazoleta.restaurantes.dominio.modelo.value.DescripcionPlato;
import com.plazoleta.restaurantes.dominio.modelo.value.PrecioPlato;
import com.plazoleta.restaurantes.dominio.modelo.value.RolPropietario;
import com.plazoleta.restaurantes.dominio.spi.PlatoRepositoryPort;
import com.plazoleta.restaurantes.dominio.spi.RestauranteRepositoryPort;
import com.plazoleta.restaurantes.dominio.spi.UsuarioValidacionPort;

public class ModificarPlato implements ModificarPlatoPort {

    private final PlatoRepositoryPort platoRepository;
    private final RestauranteRepositoryPort restauranteRepository;
    private final UsuarioValidacionPort usuarioValidacion;

    public ModificarPlato(PlatoRepositoryPort platoRepository,
                          RestauranteRepositoryPort restauranteRepository,
                          UsuarioValidacionPort usuarioValidacion) {
        this.platoRepository = platoRepository;
        this.restauranteRepository = restauranteRepository;
        this.usuarioValidacion = usuarioValidacion;
    }

    @Override
    public Plato modificarPlato(Long idPlato, Integer precio, String descripcion, Long idPropietario) {
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

        if (precio != null) {
            plato.setPrecio(new PrecioPlato(precio));
        }
        if (descripcion != null) {
            plato.setDescripcion(new DescripcionPlato(descripcion));
        }

        return platoRepository.save(plato);
    }

    private void validarPropietario(Long idPropietario) {
        UsuarioRestaurante usuario = usuarioValidacion.consultarPorId(idPropietario)
                .orElseThrow(() -> new PropietarioNoEncontradoException(
                        "El usuario con id " + idPropietario + " no existe"));
        new RolPropietario(usuario.getRol());
    }
}
