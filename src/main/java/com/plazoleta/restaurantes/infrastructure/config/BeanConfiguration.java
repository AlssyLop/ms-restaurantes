package com.plazoleta.restaurantes.infrastructure.config;

import com.plazoleta.restaurantes.dominio.api.AsociarEmpleadoPort;
import com.plazoleta.restaurantes.dominio.api.CrearPlatoPort;
import com.plazoleta.restaurantes.dominio.api.CrearRestaurantePort;
import com.plazoleta.restaurantes.dominio.api.HabilitarDeshabilitarPlatoPort;
import com.plazoleta.restaurantes.dominio.api.ModificarPlatoPort;
import com.plazoleta.restaurantes.dominio.spi.EmpleadoRestauranteRepositoryPort;
import com.plazoleta.restaurantes.dominio.spi.PlatoRepositoryPort;
import com.plazoleta.restaurantes.dominio.spi.RestauranteRepositoryPort;
import com.plazoleta.restaurantes.dominio.spi.UsuarioValidacionPort;
import com.plazoleta.restaurantes.dominio.usecase.AsociarEmpleado;
import com.plazoleta.restaurantes.dominio.usecase.CrearPlato;
import com.plazoleta.restaurantes.dominio.usecase.CrearRestaurante;
import com.plazoleta.restaurantes.dominio.usecase.GestionarPlato;
import com.plazoleta.restaurantes.dominio.usecase.ModificarPlato;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CrearRestaurantePort crearRestaurantePort(RestauranteRepositoryPort restauranteRepository,
                                                     UsuarioValidacionPort usuarioValidacion) {
        return new CrearRestaurante(restauranteRepository, usuarioValidacion);
    }

    @Bean
    public CrearPlatoPort crearPlatoPort(PlatoRepositoryPort platoRepository,
                                         RestauranteRepositoryPort restauranteRepository,
                                         UsuarioValidacionPort usuarioValidacion) {
        return new CrearPlato(platoRepository, restauranteRepository, usuarioValidacion);
    }

    @Bean
    public ModificarPlatoPort modificarPlatoPort(PlatoRepositoryPort platoRepository,
                                                 RestauranteRepositoryPort restauranteRepository,
                                                 UsuarioValidacionPort usuarioValidacion) {
        return new ModificarPlato(platoRepository, restauranteRepository, usuarioValidacion);
    }

    @Bean
    public AsociarEmpleadoPort asociarEmpleadoPort(EmpleadoRestauranteRepositoryPort repository) {
        return new AsociarEmpleado(repository);
    }

    @Bean
    public HabilitarDeshabilitarPlatoPort gestionarPlatoPort(PlatoRepositoryPort platoRepository,
                                                              RestauranteRepositoryPort restauranteRepository,
                                                              UsuarioValidacionPort usuarioValidacion) {
        return new GestionarPlato(platoRepository, restauranteRepository, usuarioValidacion);
    }
}
