# HU-9: Listar restaurantes

## Historia de usuario

> **Rol:** Cliente de la plazoleta de comidas
> **Funcionalidad:** Listar los restaurantes disponibles
> **Motivo:** Poder elegir en cuál deseo ordenar un plato

---

## Criterios de aceptación

### Campos devueltos

Por cada restaurante se devuelve:

- Nombre
- UrlLogo

### Reglas de negocio

- Los restaurantes se listan en **orden alfabético** por nombre.
- La respuesta es **paginada**. El cliente especifica el **número de página** y la **cantidad de elementos por página** como parámetros.
- Requiere **autenticación** — solo clientes logueados pueden listar restaurantes.

### Respuestas del sistema

- **Listado exitoso:** El sistema responde con la página solicitada de restaurantes más información de paginación.
- **No autenticado:** Responde 401 Unauthorized sin mensaje.
- **Página vacía:** Si la página solicitada no tiene contenido, devuelve una lista vacía con la paginación correspondiente.

---

## Endpoints (propuesta inicial)

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/restaurantes?page=0&size=10` | Listar restaurantes paginados |

### Response 200 — Listado exitoso

```json
{
  "contenido": [
    {
      "nombre": "string",
      "urlLogo": "string"
    }
  ],
  "paginaActual": 0,
  "totalPaginas": 5,
  "totalElementos": 50
}
```

### Response 401 — No autenticado

*(Sin cuerpo en la respuesta)*

---

## Suposiciones validadas

1. ✅ El cliente usa servicios REST (sin frontend).
2. ✅ La respuesta incluye información de paginación (página actual, total de páginas, total de elementos).
3. ✅ El endpoint requiere autenticación.
4. ✅ Solo se devuelven restaurantes (no platos en esta HU).
5. ✅ El cliente especifica el número de página y la cantidad de elementos por página como parámetros.
