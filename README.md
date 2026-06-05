# ms-restaurantes

Microservicio de gestion de restaurantes para la plataforma Plaza de Comidas. Implementa creacion de restaurantes con validacion de propietario via comunicacion con `ms-usuarios`.

## Stack

- Java 25 + Spring Boot 4.0.6 + Maven (mvnw wrapper)
- MySQL 8 (JPA con Hibernate, `ddl-auto=validate`)
- Spring Security + SpringDoc OpenAPI 3.0.2
- Lombok + MapStruct 1.6.3
- Pruebas: JUnit 5 + Mockito

## Arquitectura Hexagonal

```
com.plazoleta.restaurantes/
  domain/                          # nucleo puro, sin Spring
    api/          CrearRestaurantePort, CrearPlatoPort
    modelo/       Restaurante, UsuarioRestaurante, Plato
    modelo/value/ NombreRestaurante, Nit, Telefono, UrlLogo, RolPropietario,
                  NombrePlato, PrecioPlato, DescripcionPlato, UrlImagen, CategoriaPlato
    spi/          RestauranteRepositoryPort, UsuarioValidacionPort, PlatoRepositoryPort
    usecase/      CrearRestaurante, CrearPlato

  application/                      # orquestacion
    dto/request/   RestaurantePost, CrearPlatoRequest
    dto/response/  RestauranteCreado
    exception/     ErrorResponse
    factory/       RestauranteFactory, PlatoFactory
    handle/        RestauranteHandle, CrearPlatoHandle

  infrastructure/                   # adaptadores (Spring, JPA, HTTP)
    config/        BeanConfiguration, RestTemplateConfig
    endpoint/      RestauranteController, PlatoController
    endpoint/handler/ GlobalExceptionHandler
    entity/        EntidadRestaurante, EntidadPlato
    persistence/   adapter/ mapper/ repository/
    usuario/       UsuarioRestClienteAdapter
    usuario/dto/   UsuarioResponse
```

Reglas de dependencia: `infrastructure -> application -> domain`. Prohibido que `application` importe `infrastructure`.

## Base de Datos

Esquema MySQL en `db/init.sql`:

- `cargo` - CHEF, MESERO, DOMICILIARIO (seed data fija)
- `restaurante` - nombre (UNIQUE), NIT (UNIQUE), telefono, url_logo, id_propietario, activo
- `plato` - nombre, precio, categoria, FK a restaurante (tabla creada para futuras HUs)
- `empleado_restaurante` - relacion empleado-restaurante-cargo (tabla creada para futuras HUs)

Conexion local: `root/root` en `localhost:3306/plazoleta_restaurantes`.

## Ejecucion

```bash
./mvnw spring-boot:run    # Puerto 8082
./mvnw clean test         # Pruebas unitarias (23 tests)
```

## Endpoints Implementados

| Metodo | Ruta              | Descripcion                             |
|--------|-------------------|-----------------------------------------|
| POST   | `/restaurantes`   | Crear restaurante (admin)               |
| POST   | `/platos`         | Crear plato (propietario autenticado)   |

Documentacion OpenAPI disponible en `/swagger-ui.html` y `/v3/api-docs`.

## HU-2: Crear Restaurante

Crea un restaurante validando que el propietario exista en `ms-usuarios` y tenga rol `PROPIETARIO`. Endpoint restringido a usuarios autenticados como ADMINISTRADOR.

### Validaciones de dominio

- **Nombre del restaurante**: no puede contener solo numeros, unico en el sistema
- **NIT**: solo numerico, unico en el sistema
- **Telefono**: debe comenzar con `+`, maximo 13 caracteres
- **URL del logo**: debe comenzar con `http://` o `https://`
- **Propietario**: debe existir en `ms-usuarios` (via `GET /usuarios/{id}`) con rol `PROPIETARIO`

### Request body

```json
{
  "nombre": "La Tagliata",
  "nit": "123456789",
  "direccion": "Calle 123 #45-67",
  "telefono": "+573005698325",
  "urlLogo": "http://logo.com/logo.png",
  "idPropietario": 1
}
```

### Respuestas

- **201**: restaurante creado (`{ "mensaje": "Restaurante creado exitosamente" }`)
- **400**: error de validacion (nombre con solo numeros, telefono invalido, URL invalida, propietario sin rol PROPIETARIO)
- **404**: propietario no existe en `ms-usuarios`
- **409**: conflicto (nombre o NIT duplicado)

## HU-3: Crear Plato

Crea un plato asociado al restaurante del propietario. El propietario se identifica por su `idPropietario` (sin JWT por ahora).

### Validaciones de dominio (secuencial)

- **Nombre**: normalizado (trim + colapso de espacios), requerido, unico dentro del mismo restaurante
- **Precio**: entero positivo mayor a 0
- **Descripcion**: normalizada (trim + colapso de espacios), requerida
- **URL de imagen**: formato `http(s)://...` valido
- **Categoria**: normalizada (trim + colapso de espacios), requerida
- **Propietario**: debe existir en `ms-usuarios` con rol `PROPIETARIO`
- **Restaurante**: debe existir un restaurante registrado para ese propietario

### Request body

```json
{
  "nombre": "Pollo a la Brasa",
  "precio": 15000,
  "descripcion": "Delicioso pollo acompanado de papas",
  "urlImagen": "http://imagen.com/pollo.jpg",
  "categoria": "ALMUERZOS",
  "idPropietario": 1
}
```

### Respuestas

- **201**: plato creado (sin cuerpo en la respuesta)
- **400**: error de validacion (nombre/precio/descripcion/URL/categoria invalidos, propietario sin rol PROPIETARIO)
- **404**: propietario no existe o no tiene un restaurante registrado
- **409**: conflicto (nombre de plato duplicado en el restaurante)

## Proximas HU (pendientes)

- H4: Propietario modifica plato
- H7: Propietario habilita/deshabilita plato
- H9: Cliente lista restaurantes
- H10: Cliente lista platos de un restaurante
