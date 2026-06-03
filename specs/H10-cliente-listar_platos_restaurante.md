# HU-10: Listar platos de un restaurante

## Historia de usuario

> **Rol:** Cliente de la plazoleta de comidas
> **Funcionalidad:** Listar el menú de cada restaurante
> **Motivo:** Poder solicitar el plato de mi preferencia

---

## Criterios de aceptación

### Parámetros de consulta

| Parámetro | Obligatorio | Descripción |
|---|---|---|
| `idRestaurante` | Sí | En la URL para identificar el restaurante |
| `page` | Sí | Número de página a consultar |
| `size` | Sí | Cantidad de elementos por página |
| `categoria` | No | Filtro opcional por categoría de plato |

### Campos devueltos por plato

- Nombre
- Precio
- Descripción
- UrlImagen
- Categoría

### Reglas de negocio

- Los platos se listan **paginados**. El cliente especifica el **número de página** y la **cantidad de elementos por página** como parámetros.
- Se puede filtrar opcionalmente por **categoría**.
- Si no se envía filtro de categoría, se listan todos los platos del restaurante.
- Solo se devuelven platos con `activo = true`.
- La respuesta incluye información de paginación: página actual, total de páginas y total de elementos.
- Requiere **autenticación** — solo clientes logueados pueden listar el menú.

### Respuestas del sistema

- **Listado exitoso:** El sistema responde con la página solicitada de platos más información de paginación.
- **Restaurante no encontrado:** Error 404.
- **No autenticado:** 401 Unauthorized sin mensaje.

---

## Endpoints (propuesta inicial)

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/restaurantes/{idRestaurante}/platos?page=0&size=10&categoria=string` | Listar menú del restaurante |

### Response 200 — Listado exitoso

```json
{
  "contenido": [
    {
      "nombre": "string",
      "precio": 0,
      "descripcion": "string",
      "urlImagen": "string",
      "categoria": "string"
    }
  ],
  "paginaActual": 0,
  "totalPaginas": 3,
  "totalElementos": 25
}
```

### Response 401 — No autenticado

*(Sin cuerpo en la respuesta)*

### Response 404 — Restaurante no encontrado

```json
{
  "mensaje": "El restaurante no existe"
}
```

---

## Suposiciones validadas

1. ✅ El cliente envía el id del restaurante en la URL.
2. ✅ Se puede filtrar por categoría como parámetro opcional.
3. ✅ El cliente especifica el número de página y la cantidad de elementos por página como parámetros.
4. ✅ Si no se envía filtro de categoría, se listan todos los platos activos del restaurante.
5. ✅ Solo se devuelven platos con activo = true.
6. ✅ Los campos devueltos por plato son: Nombre, Precio, Descripción, UrlImagen, Categoría.
7. ✅ Requiere autenticación del cliente.
8. ✅ La respuesta incluye página actual, total de páginas y total de elementos.
