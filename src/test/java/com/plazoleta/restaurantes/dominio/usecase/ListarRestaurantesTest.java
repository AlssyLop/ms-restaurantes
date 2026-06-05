package com.plazoleta.restaurantes.dominio.usecase;

import com.plazoleta.restaurantes.dominio.modelo.Restaurante;
import com.plazoleta.restaurantes.dominio.modelo.value.Nit;
import com.plazoleta.restaurantes.dominio.modelo.value.NombreRestaurante;
import com.plazoleta.restaurantes.dominio.modelo.value.Telefono;
import com.plazoleta.restaurantes.dominio.modelo.value.UrlLogo;
import com.plazoleta.restaurantes.dominio.spi.RestauranteRepositoryPort;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarRestaurantesTest {

    @Mock
    private RestauranteRepositoryPort restauranteRepository;

    private ListarRestaurantes listarRestaurantes;

    @BeforeEach
    void setUp() {
        listarRestaurantes = new ListarRestaurantes(restauranteRepository);
    }

    @Test
    @DisplayName("Deberia listar restaurantes paginados exitosamente")
    void listarRestaurantes_ConRestaurantes_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Restaurante> restaurantes = List.of(
                new Restaurante(1L, new NombreRestaurante("La Tagliata"),
                        new Nit("123"), "Calle 1", new Telefono("+573001"),
                        new UrlLogo("http://logo.com/a.png"), 1L, true),
                new Restaurante(2L, new NombreRestaurante("El Corral"),
                        new Nit("456"), "Calle 2", new Telefono("+573002"),
                        new UrlLogo("http://logo.com/b.png"), 2L, true)
        );
        Page<Restaurante> page = new PageImpl<>(restaurantes, pageable, 2);
        when(restauranteRepository.findAllOrderedByName(pageable)).thenReturn(page);

        Page<Restaurante> result = listarRestaurantes.listarRestaurantes(pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("La Tagliata", result.getContent().get(0).getNombre().getValor());
        assertEquals("El Corral", result.getContent().get(1).getNombre().getValor());
        verify(restauranteRepository).findAllOrderedByName(pageable);
    }

    @Test
    @DisplayName("Deberia devolver pagina vacia cuando no hay restaurantes")
    void listarRestaurantes_SinRestaurantes_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Restaurante> page = Page.empty(pageable);
        when(restauranteRepository.findAllOrderedByName(pageable)).thenReturn(page);

        Page<Restaurante> result = listarRestaurantes.listarRestaurantes(pageable);

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
        verify(restauranteRepository).findAllOrderedByName(pageable);
    }
}
