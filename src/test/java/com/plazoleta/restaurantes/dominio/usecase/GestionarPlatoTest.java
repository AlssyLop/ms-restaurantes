package com.plazoleta.restaurantes.dominio.usecase;

import com.plazoleta.restaurantes.application.exception.PlatoNoEncontradoException;
import com.plazoleta.restaurantes.application.exception.PlatoYaEnEseEstadoException;
import com.plazoleta.restaurantes.application.exception.PropietarioNoEncontradoException;
import com.plazoleta.restaurantes.application.exception.SinPermisoException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestionarPlatoTest {

    @Mock
    private PlatoRepositoryPort platoRepository;

    @Mock
    private RestauranteRepositoryPort restauranteRepository;

    @Mock
    private UsuarioValidacionPort usuarioValidacion;

    private GestionarPlato gestionarPlato;

    private Plato platoHabilitado;
    private Plato platoDeshabilitado;
    private static final Long ID_PLATO = 1L;
    private static final Long ID_PROPIETARIO = 1L;
    private static final Long ID_RESTAURANTE = 10L;

    @BeforeEach
    void setUp() {
        gestionarPlato = new GestionarPlato(platoRepository, restauranteRepository, usuarioValidacion);
        platoHabilitado = new Plato(
                ID_PLATO,
                new NombrePlato("Pollo a la Brasa"),
                new PrecioPlato(15000),
                new DescripcionPlato("Delicioso pollo acompanado de papas"),
                new UrlImagen("http://imagen.com/pollo.jpg"),
                new CategoriaPlato("ALMUERZOS"),
                true,
                ID_RESTAURANTE
        );
        platoDeshabilitado = new Plato(
                ID_PLATO,
                new NombrePlato("Pollo a la Brasa"),
                new PrecioPlato(15000),
                new DescripcionPlato("Delicioso pollo acompanado de papas"),
                new UrlImagen("http://imagen.com/pollo.jpg"),
                new CategoriaPlato("ALMUERZOS"),
                false,
                ID_RESTAURANTE
        );
    }

    @Test
    @DisplayName("Deberia habilitar un plato exitosamente")
    void habilitar_AllValid_Success() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "PROPIETARIO")));
        when(platoRepository.findById(ID_PLATO)).thenReturn(Optional.of(platoDeshabilitado));
        when(restauranteRepository.findByIdPropietario(ID_PROPIETARIO))
                .thenReturn(Optional.of(new Restaurante(
                        ID_RESTAURANTE, null, null, null, null, null, ID_PROPIETARIO, true)));
        when(platoRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Plato result = gestionarPlato.habilitar(ID_PLATO, ID_PROPIETARIO);

        assertNotNull(result);
        assertTrue(result.isActivo());
        verify(platoRepository).save(any());
    }

    @Test
    @DisplayName("Deberia deshabilitar un plato exitosamente")
    void deshabilitar_AllValid_Success() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "PROPIETARIO")));
        when(platoRepository.findById(ID_PLATO)).thenReturn(Optional.of(platoHabilitado));
        when(restauranteRepository.findByIdPropietario(ID_PROPIETARIO))
                .thenReturn(Optional.of(new Restaurante(
                        ID_RESTAURANTE, null, null, null, null, null, ID_PROPIETARIO, true)));
        when(platoRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Plato result = gestionarPlato.deshabilitar(ID_PLATO, ID_PROPIETARIO);

        assertNotNull(result);
        assertFalse(result.isActivo());
        verify(platoRepository).save(any());
    }

    @Test
    @DisplayName("Deberia lanzar excepcion cuando el plato ya esta habilitado")
    void habilitar_YaHabilitado_ThrowsException() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "PROPIETARIO")));
        when(platoRepository.findById(ID_PLATO)).thenReturn(Optional.of(platoHabilitado));
        when(restauranteRepository.findByIdPropietario(ID_PROPIETARIO))
                .thenReturn(Optional.of(new Restaurante(
                        ID_RESTAURANTE, null, null, null, null, null, ID_PROPIETARIO, true)));

        PlatoYaEnEseEstadoException ex = assertThrows(PlatoYaEnEseEstadoException.class,
                () -> gestionarPlato.habilitar(ID_PLATO, ID_PROPIETARIO));
        assertEquals("El plato ya se encuentra habilitado", ex.getMessage());
        verify(platoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deberia lanzar excepcion cuando el plato ya esta deshabilitado")
    void deshabilitar_YaDeshabilitado_ThrowsException() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "PROPIETARIO")));
        when(platoRepository.findById(ID_PLATO)).thenReturn(Optional.of(platoDeshabilitado));
        when(restauranteRepository.findByIdPropietario(ID_PROPIETARIO))
                .thenReturn(Optional.of(new Restaurante(
                        ID_RESTAURANTE, null, null, null, null, null, ID_PROPIETARIO, true)));

        PlatoYaEnEseEstadoException ex = assertThrows(PlatoYaEnEseEstadoException.class,
                () -> gestionarPlato.deshabilitar(ID_PLATO, ID_PROPIETARIO));
        assertEquals("El plato ya se encuentra deshabilitado", ex.getMessage());
        verify(platoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deberia lanzar excepcion cuando el plato no existe al habilitar")
    void habilitar_PlatoNoExiste_ThrowsException() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "PROPIETARIO")));
        when(platoRepository.findById(ID_PLATO)).thenReturn(Optional.empty());

        PlatoNoEncontradoException ex = assertThrows(PlatoNoEncontradoException.class,
                () -> gestionarPlato.habilitar(ID_PLATO, ID_PROPIETARIO));
        assertEquals("El plato con id " + ID_PLATO + " no existe", ex.getMessage());
        verify(platoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deberia lanzar excepcion cuando el plato no existe al deshabilitar")
    void deshabilitar_PlatoNoExiste_ThrowsException() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "PROPIETARIO")));
        when(platoRepository.findById(ID_PLATO)).thenReturn(Optional.empty());

        PlatoNoEncontradoException ex = assertThrows(PlatoNoEncontradoException.class,
                () -> gestionarPlato.deshabilitar(ID_PLATO, ID_PROPIETARIO));
        assertEquals("El plato con id " + ID_PLATO + " no existe", ex.getMessage());
        verify(platoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deberia lanzar excepcion cuando el plato no pertenece al propietario al habilitar")
    void habilitar_SinPermiso_ThrowsException() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "PROPIETARIO")));
        when(platoRepository.findById(ID_PLATO)).thenReturn(Optional.of(platoDeshabilitado));
        when(restauranteRepository.findByIdPropietario(ID_PROPIETARIO))
                .thenReturn(Optional.of(new Restaurante(
                        99L, null, null, null, null, null, ID_PROPIETARIO, true)));

        SinPermisoException ex = assertThrows(SinPermisoException.class,
                () -> gestionarPlato.habilitar(ID_PLATO, ID_PROPIETARIO));
        assertEquals("No tienes permiso para modificar este plato", ex.getMessage());
        verify(platoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deberia lanzar excepcion cuando el plato no pertenece al propietario al deshabilitar")
    void deshabilitar_SinPermiso_ThrowsException() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "PROPIETARIO")));
        when(platoRepository.findById(ID_PLATO)).thenReturn(Optional.of(platoHabilitado));
        when(restauranteRepository.findByIdPropietario(ID_PROPIETARIO))
                .thenReturn(Optional.of(new Restaurante(
                        99L, null, null, null, null, null, ID_PROPIETARIO, true)));

        SinPermisoException ex = assertThrows(SinPermisoException.class,
                () -> gestionarPlato.deshabilitar(ID_PLATO, ID_PROPIETARIO));
        assertEquals("No tienes permiso para modificar este plato", ex.getMessage());
        verify(platoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deberia lanzar excepcion cuando el propietario no existe al habilitar")
    void habilitar_PropietarioNoExiste_ThrowsException() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO)).thenReturn(Optional.empty());

        PropietarioNoEncontradoException ex = assertThrows(PropietarioNoEncontradoException.class,
                () -> gestionarPlato.habilitar(ID_PLATO, ID_PROPIETARIO));
        assertEquals("El usuario con id " + ID_PROPIETARIO + " no existe", ex.getMessage());
        verify(platoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deberia lanzar excepcion cuando el propietario tiene rol incorrecto al habilitar")
    void habilitar_PropietarioRolIncorrecto_ThrowsException() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "CLIENTE")));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> gestionarPlato.habilitar(ID_PLATO, ID_PROPIETARIO));
        assertEquals("El rol debe ser PROPIETARIO", ex.getMessage());
        verify(platoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deberia lanzar excepcion cuando el restaurante del propietario no existe al habilitar")
    void habilitar_RestauranteNoExiste_ThrowsException() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "PROPIETARIO")));
        when(platoRepository.findById(ID_PLATO)).thenReturn(Optional.of(platoDeshabilitado));
        when(restauranteRepository.findByIdPropietario(ID_PROPIETARIO)).thenReturn(Optional.empty());

        PropietarioNoEncontradoException ex = assertThrows(PropietarioNoEncontradoException.class,
                () -> gestionarPlato.habilitar(ID_PLATO, ID_PROPIETARIO));
        assertEquals("El restaurante del usuario con id " + ID_PROPIETARIO + " no existe", ex.getMessage());
        verify(platoRepository, never()).save(any());
    }
}
