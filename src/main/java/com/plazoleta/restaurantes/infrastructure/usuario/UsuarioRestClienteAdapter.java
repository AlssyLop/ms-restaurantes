package com.plazoleta.restaurantes.infrastructure.usuario;

import com.plazoleta.restaurantes.dominio.modelo.UsuarioRestaurante;
import com.plazoleta.restaurantes.dominio.spi.UsuarioValidacionPort;
import com.plazoleta.restaurantes.infrastructure.usuario.dto.UsuarioResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

@Component
public class UsuarioRestClienteAdapter implements UsuarioValidacionPort {

    private final RestTemplate restTemplate;
    private final String msUsuariosUrl;

    public UsuarioRestClienteAdapter(RestTemplate restTemplate,
                                     @Value("${ms-usuarios.url}") String msUsuariosUrl) {
        this.restTemplate = restTemplate;
        this.msUsuariosUrl = msUsuariosUrl;
    }

    @Override
    public Optional<UsuarioRestaurante> consultarPorId(Long id) {
        try {
            UsuarioResponse response = restTemplate.getForObject(
                    msUsuariosUrl + "/usuarios/" + id,
                    UsuarioResponse.class
            );
            return Optional.of(new UsuarioRestaurante(response.getId(), response.getRol()));
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }
}
