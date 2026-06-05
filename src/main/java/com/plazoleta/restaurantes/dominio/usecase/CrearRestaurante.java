package com.plazoleta.restaurantes.dominio.usecase;

import com.plazoleta.restaurantes.dominio.api.CrearRestaurantePort;
import com.plazoleta.restaurantes.dominio.modelo.Restaurante;
import com.plazoleta.restaurantes.dominio.modelo.UsuarioRestaurante;
import com.plazoleta.restaurantes.dominio.modelo.value.RolPropietario;
import com.plazoleta.restaurantes.dominio.spi.RestauranteRepositoryPort;
import com.plazoleta.restaurantes.dominio.spi.UsuarioValidacionPort;
import org.springframework.dao.DuplicateKeyException;

public class CrearRestaurante implements CrearRestaurantePort {

    private final RestauranteRepositoryPort restauranteRepository;
    private final UsuarioValidacionPort usuarioValidacion;

    public CrearRestaurante(RestauranteRepositoryPort restauranteRepository,
                            UsuarioValidacionPort usuarioValidacion) {
        this.restauranteRepository = restauranteRepository;
        this.usuarioValidacion = usuarioValidacion;
    }

    @Override
    public Restaurante crearRestaurante(Restaurante restaurante) {
        validarPropietario(restaurante.getIdPropietario());
        validarUnicidades(restaurante);
        return restauranteRepository.save(restaurante);
    }

    private void validarPropietario(Long idPropietario) {
        UsuarioRestaurante usuario = usuarioValidacion.consultarPorId(idPropietario)
                .orElseThrow(() -> new IllegalArgumentException("El usuario con id " + idPropietario + " no existe"));
        new RolPropietario(usuario.getRol());
    }

    private void validarUnicidades(Restaurante restaurante) {
        if (restauranteRepository.existsByNombre(restaurante.getNombre())) {
            throw new DuplicateKeyException("El nombre del restaurante ya existe");
        }
        if (restauranteRepository.existsByNit(restaurante.getNit())) {
            throw new DuplicateKeyException("El NIT del restaurante ya existe");
        }
    }
}
