package com.plazoleta.restaurantes.infrastructure.config;

import com.plazoleta.restaurantes.dominio.api.CrearRestaurantePort;
import com.plazoleta.restaurantes.dominio.usecase.CrearRestaurante;
import com.plazoleta.restaurantes.dominio.spi.RestauranteRepositoryPort;
import com.plazoleta.restaurantes.dominio.spi.UsuarioValidacionPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CrearRestaurantePort crearRestaurantePort(RestauranteRepositoryPort restauranteRepository,
                                                     UsuarioValidacionPort usuarioValidacion) {
        return new CrearRestaurante(restauranteRepository, usuarioValidacion);
    }
}
