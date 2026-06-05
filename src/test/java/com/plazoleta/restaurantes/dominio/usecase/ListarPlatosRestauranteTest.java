package com.plazoleta.restaurantes.dominio.usecase;

import com.plazoleta.restaurantes.application.exception.RestauranteNoEncontradoException;
import com.plazoleta.restaurantes.dominio.modelo.Plato;
import com.plazoleta.restaurantes.dominio.modelo.Restaurante;
import com.plazoleta.restaurantes.dominio.modelo.value.CategoriaPlato;
import com.plazoleta.restaurantes.dominio.modelo.value.DescripcionPlato;
import com.plazoleta.restaurantes.dominio.modelo.value.Nit;
import com.plazoleta.restaurantes.dominio.modelo.value.NombrePlato;
import com.plazoleta.restaurantes.dominio.modelo.value.NombreRestaurante;
import com.plazoleta.restaurantes.dominio.modelo.value.PrecioPlato;
import com.plazoleta.restaurantes.dominio.modelo.value.Telefono;
import com.plazoleta.restaurantes.dominio.modelo.value.UrlImagen;
import com.plazoleta.restaurantes.dominio.modelo.value.UrlLogo;
import com.plazoleta.restaurantes.dominio.spi.PlatoRepositoryPort;
import com.plazoleta.restaurantes.dominio.spi.RestauranteRepositoryPort;
import java.util.List;
import java.util.Optional;
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
class ListarPlatosRestauranteTest {

    @Mock
    private PlatoRepositoryPort platoRepository;

    @Mock
    private RestauranteRepositoryPort restauranteRepository;

    private ListarPlatosRestaurante listarPlatosRestaurante;

    private static final Long ID_RESTAURANTE = 1L;
    private static final String CATEGORIA = "ALMUERZOS";
    private final Pageable pageable = PageRequest.of(0, 10);

    @BeforeEach
    void setUp() {
        listarPlatosRestaurante = new ListarPlatosRestaurante(platoRepository, restauranteRepository);
    }

    @Test
    @DisplayName("Deberia listar platos activos de un restaurante sin filtro de categoria")
    void listarPlatosPorRestaurante_SinCategoria_Success() {
        when(restauranteRepository.findById(ID_RESTAURANTE))
                .thenReturn(Optional.of(new Restaurante(
                        ID_RESTAURANTE, new NombreRestaurante("Test"), new Nit("123"),
                        "Dir", new Telefono("+57300"), new UrlLogo("http://logo.com"),
                        1L, true)));
        List<Plato> platos = List.of(
                new Plato(1L, new NombrePlato("Pollo"), new PrecioPlato(10000),
                        new DescripcionPlato("Rico"), new UrlImagen("http://img.com"),
                        new CategoriaPlato("ALMUERZOS"), true, ID_RESTAURANTE),
                new Plato(2L, new NombrePlato("Hamburguesa"), new PrecioPlato(15000),
                        new DescripcionPlato("Jugosa"), new UrlImagen("http://img2.com"),
                        new CategoriaPlato("COMIDA_RAPIDA"), true, ID_RESTAURANTE)
        );
        Page<Plato> page = new PageImpl<>(platos, pageable, 2);
        when(platoRepository.findByIdRestauranteAndActivoTrue(ID_RESTAURANTE, pageable)).thenReturn(page);

        Page<Plato> result = listarPlatosRestaurante.listarPlatosPorRestaurante(ID_RESTAURANTE, null, pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(restauranteRepository).findById(ID_RESTAURANTE);
        verify(platoRepository).findByIdRestauranteAndActivoTrue(ID_RESTAURANTE, pageable);
        verify(platoRepository, never()).findByIdRestauranteAndActivoTrueAndCategoria(any(), any(), any());
    }

    @Test
    @DisplayName("Deberia listar platos activos filtrados por categoria")
    void listarPlatosPorRestaurante_ConCategoria_Success() {
        when(restauranteRepository.findById(ID_RESTAURANTE))
                .thenReturn(Optional.of(new Restaurante(
                        ID_RESTAURANTE, new NombreRestaurante("Test"), new Nit("123"),
                        "Dir", new Telefono("+57300"), new UrlLogo("http://logo.com"),
                        1L, true)));
        List<Plato> platos = List.of(
                new Plato(1L, new NombrePlato("Pollo"), new PrecioPlato(10000),
                        new DescripcionPlato("Rico"), new UrlImagen("http://img.com"),
                        new CategoriaPlato(CATEGORIA), true, ID_RESTAURANTE)
        );
        Page<Plato> page = new PageImpl<>(platos, pageable, 1);
        when(platoRepository.findByIdRestauranteAndActivoTrueAndCategoria(ID_RESTAURANTE, CATEGORIA, pageable))
                .thenReturn(page);

        Page<Plato> result = listarPlatosRestaurante.listarPlatosPorRestaurante(ID_RESTAURANTE, CATEGORIA, pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(CATEGORIA, result.getContent().get(0).getCategoria().getValor());
        verify(platoRepository).findByIdRestauranteAndActivoTrueAndCategoria(ID_RESTAURANTE, CATEGORIA, pageable);
        verify(platoRepository, never()).findByIdRestauranteAndActivoTrue(any(), any());
    }

    @Test
    @DisplayName("Deberia lanzar excepcion cuando el restaurante no existe")
    void listarPlatosPorRestaurante_RestauranteNoExiste_ThrowsException() {
        when(restauranteRepository.findById(ID_RESTAURANTE)).thenReturn(Optional.empty());

        RestauranteNoEncontradoException ex = assertThrows(RestauranteNoEncontradoException.class,
                () -> listarPlatosRestaurante.listarPlatosPorRestaurante(ID_RESTAURANTE, null, pageable));
        assertEquals("El restaurante con id " + ID_RESTAURANTE + " no existe", ex.getMessage());
        verify(platoRepository, never()).findByIdRestauranteAndActivoTrue(any(), any());
        verify(platoRepository, never()).findByIdRestauranteAndActivoTrueAndCategoria(any(), any(), any());
    }

    @Test
    @DisplayName("Deberia devolver pagina vacia cuando no hay platos activos")
    void listarPlatosPorRestaurante_SinPlatos_Success() {
        when(restauranteRepository.findById(ID_RESTAURANTE))
                .thenReturn(Optional.of(new Restaurante(
                        ID_RESTAURANTE, new NombreRestaurante("Test"), new Nit("123"),
                        "Dir", new Telefono("+57300"), new UrlLogo("http://logo.com"),
                        1L, true)));
        Page<Plato> page = Page.empty(pageable);
        when(platoRepository.findByIdRestauranteAndActivoTrue(ID_RESTAURANTE, pageable)).thenReturn(page);

        Page<Plato> result = listarPlatosRestaurante.listarPlatosPorRestaurante(ID_RESTAURANTE, null, pageable);

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
    }
}
