package com.plazoleta.restaurantes.dominio.usecase;

import com.plazoleta.restaurantes.dominio.modelo.Restaurante;
import com.plazoleta.restaurantes.dominio.modelo.UsuarioRestaurante;
import com.plazoleta.restaurantes.dominio.modelo.value.Nit;
import com.plazoleta.restaurantes.dominio.modelo.value.NombreRestaurante;
import com.plazoleta.restaurantes.dominio.modelo.value.Telefono;
import com.plazoleta.restaurantes.dominio.modelo.value.UrlLogo;
import com.plazoleta.restaurantes.dominio.spi.RestauranteRepositoryPort;
import com.plazoleta.restaurantes.dominio.spi.UsuarioValidacionPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrearRestauranteTest {

    @Mock
    private RestauranteRepositoryPort restauranteRepository;

    @Mock
    private UsuarioValidacionPort usuarioValidacion;

    private CrearRestaurante crearRestaurante;

    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        crearRestaurante = new CrearRestaurante(restauranteRepository, usuarioValidacion);
        restaurante = new Restaurante(
                null,
                new NombreRestaurante("La Tagliata"),
                new Nit("123456789"),
                "Calle 123 #45-67",
                new Telefono("+573005698325"),
                new UrlLogo("http://logo.com/logo.png"),
                1L,
                true
        );
    }

    @Test
    @DisplayName("Should create restaurant successfully with all valid fields")
    void crearRestaurante_AllValid_Success() {
        when(usuarioValidacion.consultarPorId(1L))
                .thenReturn(Optional.of(new UsuarioRestaurante(1L, "PROPIETARIO")));
        when(restauranteRepository.existsByNombre(any())).thenReturn(false);
        when(restauranteRepository.existsByNit(any())).thenReturn(false);
        Restaurante saved = new Restaurante(
                1L,
                new NombreRestaurante("La Tagliata"),
                new Nit("123456789"),
                "Calle 123 #45-67",
                new Telefono("+573005698325"),
                new UrlLogo("http://logo.com/logo.png"),
                1L,
                true
        );
        when(restauranteRepository.save(restaurante)).thenReturn(saved);

        Restaurante result = crearRestaurante.crearRestaurante(restaurante);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("La Tagliata", result.getNombre().getValor());
        verify(usuarioValidacion).consultarPorId(1L);
        verify(restauranteRepository).existsByNombre(any());
        verify(restauranteRepository).existsByNit(any());
        verify(restauranteRepository).save(restaurante);
    }

    @Test
    @DisplayName("Should throw exception when propietario does not exist")
    void crearRestaurante_PropietarioNoExiste_ThrowsException() {
        when(usuarioValidacion.consultarPorId(1L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> crearRestaurante.crearRestaurante(restaurante));
        assertEquals("El usuario con id 1 no existe", ex.getMessage());
        verify(restauranteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when propietario has wrong role")
    void crearRestaurante_PropietarioRolIncorrecto_ThrowsException() {
        when(usuarioValidacion.consultarPorId(1L))
                .thenReturn(Optional.of(new UsuarioRestaurante(1L, "CLIENTE")));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> crearRestaurante.crearRestaurante(restaurante));
        assertEquals("El rol debe ser PROPIETARIO", ex.getMessage());
        verify(restauranteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when nombre already exists")
    void crearRestaurante_NombreDuplicado_ThrowsException() {
        when(usuarioValidacion.consultarPorId(1L))
                .thenReturn(Optional.of(new UsuarioRestaurante(1L, "PROPIETARIO")));
        when(restauranteRepository.existsByNombre(restaurante.getNombre())).thenReturn(true);

        DuplicateKeyException ex = assertThrows(DuplicateKeyException.class,
                () -> crearRestaurante.crearRestaurante(restaurante));
        assertEquals("El nombre del restaurante ya existe", ex.getMessage());
        verify(restauranteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when NIT already exists")
    void crearRestaurante_NitDuplicado_ThrowsException() {
        when(usuarioValidacion.consultarPorId(1L))
                .thenReturn(Optional.of(new UsuarioRestaurante(1L, "PROPIETARIO")));
        when(restauranteRepository.existsByNombre(any())).thenReturn(false);
        when(restauranteRepository.existsByNit(restaurante.getNit())).thenReturn(true);

        DuplicateKeyException ex = assertThrows(DuplicateKeyException.class,
                () -> crearRestaurante.crearRestaurante(restaurante));
        assertEquals("El NIT del restaurante ya existe", ex.getMessage());
        verify(restauranteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when nombre contains only numbers")
    void crearRestaurante_NombreSoloNumeros_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new NombreRestaurante("12345"));
        verifyNoInteractions(restauranteRepository, usuarioValidacion);
    }

    @Test
    @DisplayName("Should throw exception when NIT is not numeric")
    void crearRestaurante_NitInvalido_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Nit("ABC123"));
        verifyNoInteractions(restauranteRepository, usuarioValidacion);
    }

    @Test
    @DisplayName("Should throw exception when telefono does not start with +")
    void crearRestaurante_TelefonoInvalido_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Telefono("573005698325"));
        verifyNoInteractions(restauranteRepository, usuarioValidacion);
    }

    @Test
    @DisplayName("Should throw exception when telefono exceeds 13 characters")
    void crearRestaurante_TelefonoMuyLargo_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Telefono("+12345678901234"));
        verifyNoInteractions(restauranteRepository, usuarioValidacion);
    }

    @Test
    @DisplayName("Should throw exception when urlLogo has invalid format")
    void crearRestaurante_UrlLogoInvalido_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new UrlLogo("ftp://logo.com/logo.png"));
        verifyNoInteractions(restauranteRepository, usuarioValidacion);
    }

    @Test
    @DisplayName("Should throw exception when idPropietario is null")
    void crearRestaurante_IdPropietarioNulo_ThrowsException() {
        when(usuarioValidacion.consultarPorId(null)).thenReturn(Optional.empty());

        Restaurante sinPropietario = new Restaurante(
                null,
                new NombreRestaurante("Test"),
                new Nit("999999"),
                "Dir",
                new Telefono("+573005698325"),
                new UrlLogo("http://logo.com/logo.png"),
                null,
                true
        );

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> crearRestaurante.crearRestaurante(sinPropietario));
        assertEquals("El usuario con id null no existe", ex.getMessage());
        verify(restauranteRepository, never()).save(any());
    }
}
