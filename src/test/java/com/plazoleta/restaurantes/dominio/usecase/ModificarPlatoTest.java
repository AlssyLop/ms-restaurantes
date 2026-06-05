package com.plazoleta.restaurantes.dominio.usecase;

import com.plazoleta.restaurantes.application.exception.PlatoNoEncontradoException;
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
class ModificarPlatoTest {

    @Mock
    private PlatoRepositoryPort platoRepository;

    @Mock
    private RestauranteRepositoryPort restauranteRepository;

    @Mock
    private UsuarioValidacionPort usuarioValidacion;

    private ModificarPlato modificarPlato;

    private Plato platoExistente;
    private static final Long ID_PLATO = 1L;
    private static final Long ID_PROPIETARIO = 1L;
    private static final Long ID_RESTAURANTE = 10L;

    @BeforeEach
    void setUp() {
        modificarPlato = new ModificarPlato(platoRepository, restauranteRepository, usuarioValidacion);
        platoExistente = new Plato(
                ID_PLATO,
                new NombrePlato("Pollo a la Brasa"),
                new PrecioPlato(15000),
                new DescripcionPlato("Delicioso pollo acompanado de papas"),
                new UrlImagen("http://imagen.com/pollo.jpg"),
                new CategoriaPlato("ALMUERZOS"),
                true,
                ID_RESTAURANTE
        );
    }

    @Test
    @DisplayName("Deberia modificar precio y descripcion exitosamente")
    void modificarPlato_AllValid_Success() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "PROPIETARIO")));
        when(platoRepository.findById(ID_PLATO)).thenReturn(Optional.of(platoExistente));
        when(restauranteRepository.findByIdPropietario(ID_PROPIETARIO))
                .thenReturn(Optional.of(new Restaurante(
                        ID_RESTAURANTE, null, null, null, null, null, ID_PROPIETARIO, true)));
        Plato platoModificado = new Plato(
                ID_PLATO,
                new NombrePlato("Pollo a la Brasa"),
                new PrecioPlato(20000),
                new DescripcionPlato("Nueva descripcion actualizada"),
                new UrlImagen("http://imagen.com/pollo.jpg"),
                new CategoriaPlato("ALMUERZOS"),
                true,
                ID_RESTAURANTE
        );
        when(platoRepository.save(any())).thenReturn(platoModificado);

        Plato result = modificarPlato.modificarPlato(ID_PLATO, 20000, "Nueva descripcion actualizada", ID_PROPIETARIO);

        assertNotNull(result);
        assertEquals(20000, result.getPrecio().getValor());
        assertEquals("Nueva descripcion actualizada", result.getDescripcion().getValor());
        verify(platoRepository).save(any());
    }

    @Test
    @DisplayName("Deberia modificar solo el precio cuando descripcion es null")
    void modificarPlato_SoloPrecio_Success() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "PROPIETARIO")));
        when(platoRepository.findById(ID_PLATO)).thenReturn(Optional.of(platoExistente));
        when(restauranteRepository.findByIdPropietario(ID_PROPIETARIO))
                .thenReturn(Optional.of(new Restaurante(
                        ID_RESTAURANTE, null, null, null, null, null, ID_PROPIETARIO, true)));
        when(platoRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Plato result = modificarPlato.modificarPlato(ID_PLATO, 25000, null, ID_PROPIETARIO);

        assertEquals(25000, result.getPrecio().getValor());
        assertEquals("Delicioso pollo acompanado de papas", result.getDescripcion().getValor());
    }

    @Test
    @DisplayName("Deberia modificar solo la descripcion cuando precio es null")
    void modificarPlato_SoloDescripcion_Success() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "PROPIETARIO")));
        when(platoRepository.findById(ID_PLATO)).thenReturn(Optional.of(platoExistente));
        when(restauranteRepository.findByIdPropietario(ID_PROPIETARIO))
                .thenReturn(Optional.of(new Restaurante(
                        ID_RESTAURANTE, null, null, null, null, null, ID_PROPIETARIO, true)));
        when(platoRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Plato result = modificarPlato.modificarPlato(ID_PLATO, null, "Solo descripcion nueva", ID_PROPIETARIO);

        assertEquals(15000, result.getPrecio().getValor());
        assertEquals("Solo descripcion nueva", result.getDescripcion().getValor());
    }

    @Test
    @DisplayName("Deberia lanzar excepcion cuando el propietario no existe")
    void modificarPlato_PropietarioNoExiste_ThrowsException() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO)).thenReturn(Optional.empty());

        PropietarioNoEncontradoException ex = assertThrows(PropietarioNoEncontradoException.class,
                () -> modificarPlato.modificarPlato(ID_PLATO, 20000, "nueva", ID_PROPIETARIO));
        assertEquals("El usuario con id " + ID_PROPIETARIO + " no existe", ex.getMessage());
        verify(platoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deberia lanzar excepcion cuando el propietario tiene rol incorrecto")
    void modificarPlato_PropietarioRolIncorrecto_ThrowsException() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "CLIENTE")));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> modificarPlato.modificarPlato(ID_PLATO, 20000, "nueva", ID_PROPIETARIO));
        assertEquals("El rol debe ser PROPIETARIO", ex.getMessage());
        verify(platoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deberia lanzar excepcion cuando el plato no existe")
    void modificarPlato_PlatoNoExiste_ThrowsException() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "PROPIETARIO")));
        when(platoRepository.findById(ID_PLATO)).thenReturn(Optional.empty());

        PlatoNoEncontradoException ex = assertThrows(PlatoNoEncontradoException.class,
                () -> modificarPlato.modificarPlato(ID_PLATO, 20000, "nueva", ID_PROPIETARIO));
        assertEquals("El plato con id " + ID_PLATO + " no existe", ex.getMessage());
        verify(platoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deberia lanzar excepcion cuando el restaurante no existe")
    void modificarPlato_RestauranteNoExiste_ThrowsException() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "PROPIETARIO")));
        when(platoRepository.findById(ID_PLATO)).thenReturn(Optional.of(platoExistente));
        when(restauranteRepository.findByIdPropietario(ID_PROPIETARIO)).thenReturn(Optional.empty());

        PropietarioNoEncontradoException ex = assertThrows(PropietarioNoEncontradoException.class,
                () -> modificarPlato.modificarPlato(ID_PLATO, 20000, "nueva", ID_PROPIETARIO));
        assertEquals("El restaurante del usuario con id " + ID_PROPIETARIO + " no existe", ex.getMessage());
        verify(platoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deberia lanzar excepcion cuando el plato no pertenece al propietario")
    void modificarPlato_SinPermiso_ThrowsException() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "PROPIETARIO")));
        when(platoRepository.findById(ID_PLATO)).thenReturn(Optional.of(platoExistente));
        when(restauranteRepository.findByIdPropietario(ID_PROPIETARIO))
                .thenReturn(Optional.of(new Restaurante(
                        99L, null, null, null, null, null, ID_PROPIETARIO, true)));

        SinPermisoException ex = assertThrows(SinPermisoException.class,
                () -> modificarPlato.modificarPlato(ID_PLATO, 20000, "nueva", ID_PROPIETARIO));
        assertEquals("No tienes permiso para modificar este plato", ex.getMessage());
        verify(platoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deberia lanzar excepcion cuando el precio es invalido")
    void modificarPlato_PrecioInvalido_ThrowsException() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "PROPIETARIO")));
        when(platoRepository.findById(ID_PLATO)).thenReturn(Optional.of(platoExistente));
        when(restauranteRepository.findByIdPropietario(ID_PROPIETARIO))
                .thenReturn(Optional.of(new Restaurante(
                        ID_RESTAURANTE, null, null, null, null, null, ID_PROPIETARIO, true)));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> modificarPlato.modificarPlato(ID_PLATO, 0, null, ID_PROPIETARIO));
        assertEquals("El precio no es v\u00E1lido", ex.getMessage());
        verify(platoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deberia lanzar excepcion cuando la descripcion esta vacia")
    void modificarPlato_DescripcionVacia_ThrowsException() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "PROPIETARIO")));
        when(platoRepository.findById(ID_PLATO)).thenReturn(Optional.of(platoExistente));
        when(restauranteRepository.findByIdPropietario(ID_PROPIETARIO))
                .thenReturn(Optional.of(new Restaurante(
                        ID_RESTAURANTE, null, null, null, null, null, ID_PROPIETARIO, true)));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> modificarPlato.modificarPlato(ID_PLATO, null, "   ", ID_PROPIETARIO));
        assertEquals("La descripcion del plato es requerida", ex.getMessage());
        verify(platoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deberia normalizar espacios en la descripcion")
    void modificarPlato_NormalizaDescripcion_Success() {
        when(usuarioValidacion.consultarPorId(ID_PROPIETARIO))
                .thenReturn(Optional.of(new UsuarioRestaurante(ID_PROPIETARIO, "PROPIETARIO")));
        when(platoRepository.findById(ID_PLATO)).thenReturn(Optional.of(platoExistente));
        when(restauranteRepository.findByIdPropietario(ID_PROPIETARIO))
                .thenReturn(Optional.of(new Restaurante(
                        ID_RESTAURANTE, null, null, null, null, null, ID_PROPIETARIO, true)));
        when(platoRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Plato result = modificarPlato.modificarPlato(ID_PLATO, null,
                "   nueva   descripcion    con   espacios   ", ID_PROPIETARIO);

        assertEquals("nueva descripcion con espacios", result.getDescripcion().getValor());
    }
}
