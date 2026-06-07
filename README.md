# ms-restaurantes

Microservicio de gestion de restaurantes para la plataforma Plaza de Comidas. Implementa creacion de restaurantes con validacion de propietario via comunicacion con `ms-usuarios`.

## Stack

- Java 25 + Spring Boot 4.0.6 + Maven (mvnw wrapper)
- MySQL 8 (JPA con Hibernate, `ddl-auto=validate`)
- Spring Security + jjwt 0.12.6 + SpringDoc OpenAPI 3.0.2
- Lombok + MapStruct 1.6.3
- Pruebas: JUnit 5 + Mockito

## Arquitectura Hexagonal

```
com.plazoleta.restaurantes/
  domain/                          # nucleo puro, sin Spring
    api/          CrearRestaurantePort, CrearPlatoPort, ModificarPlatoPort, AsociarEmpleadoPort,
                   HabilitarDeshabilitarPlatoPort
    modelo/       Restaurante, UsuarioRestaurante, Plato, EmpleadoRestaurante
    modelo/value/ NombreRestaurante, Nit, Telefono, UrlLogo, RolPropietario,
                  NombrePlato, PrecioPlato, DescripcionPlato, UrlImagen, CategoriaPlato
    spi/          RestauranteRepositoryPort, UsuarioValidacionPort, PlatoRepositoryPort,
                  EmpleadoRestauranteRepositoryPort
    usecase/      CrearRestaurante, CrearPlato, ModificarPlato, AsociarEmpleado, GestionarPlato

  application/                      # orquestacion
    dto/request/   RestaurantePost, CrearPlatoRequest, ModificarPlatoRequest, AsociarEmpleadoRequest
    dto/response/  RestauranteCreado, CrearPlatoResponse, ModificarPlatoResponse, AsociarEmpleadoResponse,
                   GestionarPlatoResponse
    exception/     ErrorResponse, PlatoYaEnEseEstadoException
    factory/       RestauranteFactory, PlatoFactory, EmpleadoRestauranteFactory
    handle/        RestauranteHandle, CrearPlatoHandle, ModificarPlatoHandle, EmpleadoRestauranteHandle,
                   GestionarPlatoHandle

  infrastructure/                   # adaptadores (Spring, JPA, HTTP)
    config/        BeanConfiguration, RestTemplateConfig
    endpoint/      RestauranteController, PlatoController, EmpleadoRestauranteController
    endpoint/handler/ GlobalExceptionHandler
    entity/        EntidadRestaurante, EntidadPlato, EntidadEmpleadoRestaurante
    persistence/   adapter/ mapper/ repository/
    security/      SecurityConfig (JWT filter chain)
    security/jwt/  JwtTokenProvider, JwtAuthenticationFilter
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
./mvnw clean test         # Pruebas unitarias (52 tests)
```

## Endpoints Implementados

| Metodo | Ruta                | Descripcion                               | Autenticacion  |
|--------|---------------------|-------------------------------------------|----------------|
| POST   | `/restaurantes`     | Crear restaurante                         | ADMINISTRADOR  |
| POST   | `/platos`           | Crear plato                               | PROPIETARIO    |
| PUT    | `/platos/{idPlato}` | Modificar precio/descripcion de un plato  | PROPIETARIO    |
| PATCH  | `/platos/{idPlato}/habilitar`   | Habilitar plato             | PROPIETARIO    |
| PATCH  | `/platos/{idPlato}/deshabilitar`| Deshabilitar plato          | PROPIETARIO    |
| POST   | `/restaurantes/empleados` | Asociar empleado a restaurante       | PROPIETARIO    |
| GET    | `/restaurantes?page=0&size=10` | Listar restaurantes paginados       | CLIENTE        |
| GET    | `/restaurantes/{idRestaurante}/platos?page=0&size=10&categoria=` | Listar platos del restaurante | CLIENTE |

Documentacion OpenAPI disponible en `/swagger-ui.html` y `/v3/api-docs`.

## Seguridad JWT

Todos los endpoints (excepto OpenAPI) requieren un token JWT válido emitido por `ms-usuarios`. El token se envía vía header:

```
Authorization: Bearer <token>
```

- **401** — token ausente, inválido o expirado (sin body)
- **403** — token válido pero rol insuficiente (sin body)

La validación usa la **llave pública RSA-4096** (`jwt.public-key`) proporcionada por `ms-usuarios`.

---

## HU-2: Crear Restaurante

Crea un restaurante validando que el propietario exista en `ms-usuarios` y tenga rol `PROPIETARIO`. Endpoint restringido a ADMINISTRADOR.

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

Crea un plato asociado al restaurante del propietario autenticado (rol PROPIETARIO requerido).

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

- **201**: plato creado (`{ "mensaje": "Plato creado exitosamente" }`)
- **400**: error de validacion (nombre/precio/descripcion/URL/categoria invalidos, propietario sin rol PROPIETARIO)
- **404**: propietario no existe o no tiene un restaurante registrado
- **409**: conflicto (nombre de plato duplicado en el restaurante)

## HU-4: Modificar Plato

Modifica el precio y/o descripción de un plato existente. El propietario autenticado (rol PROPIETARIO) debe ser el dueño del restaurante al que pertenece el plato.

### Request body

```json
{
  "precio": 18000,
  "descripcion": "Nueva descripcion del plato"
}
```

### Respuestas

- **200**: plato modificado (`{ "mensaje": "Plato modificado exitosamente" }`)
- **400**: error de validacion (precio o descripcion invalidos)
- **403**: el propietario no es dueno del restaurante del plato
- **404**: plato no encontrado

---

## HU-6: Asociar Empleado a Restaurante

Asocia un empleado al restaurante del propietario autenticado. Llamado por `ms-usuarios` via RestTemplate despues de crear la cuenta del empleado. Endpoint protegido (requiere JWT de Propietario).

### Validaciones de dominio

- **Empleado duplicado**: no se puede asociar el mismo empleado dos veces al mismo restaurante

### Request body

```json
{
  "idEmpleado": 1,
  "idCargo": 1
}
```

### Respuestas

- **201**: `{"mensaje": "Empleado asociado exitosamente"}`
- **400**: error de validacion (empleado duplicado)
- **404**: no se encontro restaurante para el propietario autenticado

---

## HU-7: Habilitar/Deshabilitar Plato

Habilita o deshabilita un plato del menú del restaurante del propietario autenticado (rol PROPIETARIO requerido).

### Endpoints

| Metodo | Ruta                       | Descripcion             | Autenticacion  |
|--------|----------------------------|-------------------------|----------------|
| PATCH  | `/platos/{idPlato}/habilitar`   | Habilitar plato         | PROPIETARIO    |
| PATCH  | `/platos/{idPlato}/deshabilitar`| Deshabilitar plato      | PROPIETARIO    |

### Validaciones de dominio

- **Propietario**: debe existir en `ms-usuarios` con rol `PROPIETARIO`
- **Restaurante**: debe existir un restaurante registrado para ese propietario
- **Plato**: debe existir y pertenecer al restaurante del propietario
- **Estado**: si el plato ya está en el estado solicitado, se rechaza la operación

### Respuestas

- **200**: operación exitosa
  - `{ "mensaje": "Plato habilitado exitosamente" }`
  - `{ "mensaje": "Plato deshabilitado exitosamente" }`
- **400**: el plato ya se encuentra en ese estado (`{ "mensaje": "El plato ya se encuentra habilitado/deshabilitado" }`)
- **403**: el propietario no es dueño del restaurante del plato
- **404**: plato o propietario no encontrado

## HU-9: Listar Restaurantes

Lista los restaurantes disponibles paginados, en orden alfabético por nombre. Requiere autenticación como CLIENTE.

### Endpoint

| Metodo | Ruta | Descripcion | Autenticacion  |
|--------|------|-------------|----------------|
| GET    | `/restaurantes?page=0&size=10` | Listar restaurantes paginados | CLIENTE |

### Response 200

```json
{
  "contenido": [
    { "nombre": "La Tagliata", "urlLogo": "http://logo.com/logo.png" }
  ],
  "paginaActual": 0,
  "totalPaginas": 5,
  "totalElementos": 50
}
```

## HU-10: Listar Platos de un Restaurante

Lista los platos activos de un restaurante, con paginación y filtro opcional por categoría. Requiere autenticación como CLIENTE.

### Endpoint

| Metodo | Ruta | Descripcion | Autenticacion  |
|--------|------|-------------|----------------|
| GET    | `/restaurantes/{idRestaurante}/platos?page=0&size=10&categoria=string` | Listar platos del restaurante | CLIENTE |

### Response 200

```json
{
  "contenido": [
    {
      "nombre": "Pollo a la Brasa",
      "precio": 15000,
      "descripcion": "Delicioso pollo acompanado de papas",
      "urlImagen": "http://imagen.com/pollo.jpg",
      "categoria": "ALMUERZOS"
    }
  ],
  "paginaActual": 0,
  "totalPaginas": 3,
  "totalElementos": 25
}
```

### Response 404

Restaurante no encontrado (sin body).
