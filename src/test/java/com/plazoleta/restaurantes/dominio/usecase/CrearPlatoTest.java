package com.plazoleta.restaurantes.dominio.usecase;

import com.plazoleta.restaurantes.dominio.modelo.Plato;
import com.plazoleta.restaurantes.dominio.modelo.Restaurante;
import com.plazoleta.restaurantes.dominio.modelo.UsuarioRestaurante;
import com.plazoleta.restaurantes.dominio.modelo.value.CategoriaPlato;
import com.plazoleta.restaurantes.dominio.modelo.value.DescripcionPlato;
import com.plazoleta.restaurantes.dominio.modelo.value.NombrePlato;
import com.plazoleta.restaurantes.dominio.modelo.value.PrecioPlato;
import com.plazoleta.restaurantes.dominio.modelo.value.UrlImagen;
import com.plazoleta.restaurantes.dominio.spi.PlatoRepositoryPort;
import com.plazoleta.restaurantes.dominio.spi.RestauranteRepositoryPort;
import com.plazoleta.restaurantes.dominio.spi.UsuarioValidacionPort;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrearPlatoTest {

    @Mock
    private PlatoRepositoryPort platoRepository;

    @Mock
    private RestauranteRepositoryPort restauranteRepository;

    @Mock
    private UsuarioValidacionPort usuarioValidacion;

    private CrearPlato crearPlato;

    private Plato plato;
    private static final Long ID_PROPIETARIO = 1L;
    private static final Long ID_RESTAURANTE = 10L;

    @BeforeEach
    void setUp() {
        crearPlato = new CrearPlato(platoRepository, restauranteRepository, usuarioValidacion);
        plato = new Plato(
                null,
                new NombrePlato("Pollo a la Brasa"),
                new PrecioPlato(15000),
                new DescripcionPlato("Delicioso pollo acompanado de papas"),
                new UrlImagen("http://imagen.com/pollo.jpg"),
                new CategoriaPlato("ALMUERZOS"),
                true,
                null
        );
    }

    @Test
    @DisplayName("Should create plato successfully with all valid fields")
    void crearPlato_AllValid_Success() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "PROPIETARIO")));
        when(restauranteRepository.findByIdPropietario(ID_PROPIETARIO))
                .thenReturn(Optional.of(new Restaurante(
                        ID_RESTAURANTE, null, null, null, null, null, ID_PROPIETARIO, true)));
        when(platoRepository.existsByNombreAndIdRestaurante(any(), anyLong())).thenReturn(false);
        Plato saved = new Plato(
                1L,
                new NombrePlato("Pollo a la Brasa"),
                new PrecioPlato(15000),
                new DescripcionPlato("Delicioso pollo acompanado de papas"),
                new UrlImagen("http://imagen.com/pollo.jpg"),
                new CategoriaPlato("ALMUERZOS"),
                true,
                ID_RESTAURANTE
        );
        when(platoRepository.save(any())).thenReturn(saved);

        Plato result = crearPlato.crearPlato(plato, ID_PROPIETARIO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Pollo a la Brasa", result.getNombre().getValor());
        assertEquals(ID_RESTAURANTE, result.getIdRestaurante());
        verify(usuarioValidacion).consultarPorId(ID_PROPIETARIO);
        verify(restauranteRepository).findByIdPropietario(ID_PROPIETARIO);
        verify(platoRepository).existsByNombreAndIdRestaurante(any(), anyLong());
        verify(platoRepository).save(any());
    }

    @Test
    @DisplayName("Should throw exception when propietario does not exist")
    void crearPlato_PropietarioNoExiste_ThrowsException() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> crearPlato.crearPlato(plato, ID_PROPIETARIO));
        assertEquals("El usuario con id " + ID_PROPIETARIO + " no existe", ex.getMessage());
        verify(platoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when propietario has wrong role")
    void crearPlato_PropietarioRolIncorrecto_ThrowsException() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "CLIENTE")));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> crearPlato.crearPlato(plato, ID_PROPIETARIO));
        assertEquals("El rol debe ser PROPIETARIO", ex.getMessage());
        verify(platoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when restaurant not found for propietario")
    void crearPlato_RestauranteNoExiste_ThrowsException() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "PROPIETARIO")));
        when(restauranteRepository.findByIdPropietario(ID_PROPIETARIO)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> crearPlato.crearPlato(plato, ID_PROPIETARIO));
        assertEquals("El restaurante del usuario con id " + ID_PROPIETARIO + " no existe", ex.getMessage());
        verify(platoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when plato name already exists in restaurant")
    void crearPlato_NombreDuplicado_ThrowsException() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "PROPIETARIO")));
        when(restauranteRepository.findByIdPropietario(ID_PROPIETARIO))
                .thenReturn(Optional.of(new Restaurante(
                        ID_RESTAURANTE, null, null, null, null, null, ID_PROPIETARIO, true)));
        when(platoRepository.existsByNombreAndIdRestaurante(any(), anyLong())).thenReturn(true);

        DuplicateKeyException ex = assertThrows(DuplicateKeyException.class,
                () -> crearPlato.crearPlato(plato, ID_PROPIETARIO));
        assertTrue(ex.getMessage().contains("ya existe en el restaurante"));
        verify(platoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when precio is zero")
    void crearPlato_PrecioCero_ThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new PrecioPlato(0));
        assertEquals("El precio debe ser un numero entero positivo mayor a 0", ex.getMessage());
        verifyNoInteractions(platoRepository, restauranteRepository, usuarioValidacion);
    }

    @Test
    @DisplayName("Should throw exception when precio is negative")
    void crearPlato_PrecioNegativo_ThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new PrecioPlato(-1));
        assertEquals("El precio debe ser un numero entero positivo mayor a 0", ex.getMessage());
        verifyNoInteractions(platoRepository, restauranteRepository, usuarioValidacion);
    }

    @Test
    @DisplayName("Should throw exception when urlImagen has invalid format")
    void crearPlato_UrlImagenInvalida_ThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new UrlImagen("ftp://imagen.com/foto.jpg"));
        assertEquals("La URL de la imagen no tiene un formato valido", ex.getMessage());
        verifyNoInteractions(platoRepository, restauranteRepository, usuarioValidacion);
    }

    @Test
    @DisplayName("Should throw exception when nombre is empty")
    void crearPlato_NombreVacio_ThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new NombrePlato("   "));
        assertEquals("El nombre del plato es requerido", ex.getMessage());
        verifyNoInteractions(platoRepository, restauranteRepository, usuarioValidacion);
    }

    @Test
    @DisplayName("Should throw exception when descripcion is empty")
    void crearPlato_DescripcionVacia_ThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new DescripcionPlato("   "));
        assertEquals("La descripcion del plato es requerida", ex.getMessage());
        verifyNoInteractions(platoRepository, restauranteRepository, usuarioValidacion);
    }

    @Test
    @DisplayName("Should throw exception when categoria is empty")
    void crearPlato_CategoriaVacia_ThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new CategoriaPlato("   "));
        assertEquals("La categoria del plato es requerida", ex.getMessage());
        verifyNoInteractions(platoRepository, restauranteRepository, usuarioValidacion);
    }

    @Test
    @DisplayName("Should normalize spaces in nombre, descripcion and categoria")
    void crearPlato_NormalizaEspacios_Success() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "PROPIETARIO")));
        when(restauranteRepository.findByIdPropietario(ID_PROPIETARIO))
                .thenReturn(Optional.of(new Restaurante(
                        ID_RESTAURANTE, null, null, null, null, null, ID_PROPIETARIO, true)));
        when(platoRepository.existsByNombreAndIdRestaurante(any(), anyLong())).thenReturn(false);
        when(platoRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Plato conEspacios = new Plato(
                null,
                new NombrePlato("  plato  papas    con    salsa   "),
                new PrecioPlato(15000),
                new DescripcionPlato("  delicioso  plato   "),
                new UrlImagen("http://imagen.com/plato.jpg"),
                new CategoriaPlato("  almuerzos  "),
                true,
                null
        );

        Plato result = crearPlato.crearPlato(conEspacios, ID_PROPIETARIO);

        assertEquals("plato papas con salsa", result.getNombre().getValor());
        assertEquals("delicioso plato", result.getDescripcion().getValor());
        assertEquals("almuerzos", result.getCategoria().getValor());
    }
}
