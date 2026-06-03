# HU-7: Habilitar/Deshabilitar plato

## Historia de usuario

> **Rol:** Propietario de un restaurante
> **Funcionalidad:** Activar/desactivar platos en el menú
> **Motivo:** Dejar de ofrecer el producto en el menú

---

## Criterios de aceptación

### Reglas de negocio

- Solo el propietario puede habilitar/deshabilitar platos.
- No se permiten modificar platos de otros restaurantes diferentes al propio.
- El propietario debe estar autenticado y tener el rol **PROPIETARIO**.
- El plato se identifica por su ID en la URL.
- Si el plato ya está en el estado solicitado (ej. ya está deshabilitado y se pide deshabilitar), el sistema responde con un mensaje indicándolo.

### Respuestas del sistema

- **Operación exitosa:** El sistema responde con un mensaje de confirmación.
- **Error (no encontrado / no pertenece a su restaurante):** El sistema responde con el error correspondiente.
- **Error (ya está en ese estado):** El sistema responde con un mensaje indicándolo.

---

## Endpoints (propuesta inicial)

| Método | Ruta | Descripción |
|---|---|---|
| `PATCH` | `platos/{id}/habilitar` | Habilitar plato |
| `PATCH` | `platos/{id}/deshabilitar` | Deshabilitar plato |

### Response 200 — Operación exitosa

```json
{
  "mensaje": "Plato habilitado exitosamente"
}
```

```json
{
  "mensaje": "Plato deshabilitado exitosamente"
}
```

### Response 400 — Ya está en ese estado

```json
{
  "mensaje": "El plato ya se encuentra habilitado"
}
```

```json
{
  "mensaje": "El plato ya se encuentra deshabilitado"
}
```

### Response 404 — No encontrado / otro restaurante

```json
{
  "mensaje": "El plato no existe"
}
```

---

## Suposiciones validadas

1. ✅ El propietario envía la solicitud a través de servicios REST (sin frontend).
2. ✅ Si la operación es exitosa, el sistema responde con un mensaje de confirmación.
3. ✅ Si hay errores (plato no encontrado, no es de su restaurante), el sistema responde con el error correspondiente.
4. ✅ El propietario debe estar autenticado y tener el rol PROPIETARIO.
5. ✅ El plato se identifica por su ID en la URL.
6. ✅ Se usan dos endpoints separados: uno para habilitar y otro para deshabilitar.
7. ✅ Si el plato ya está en el estado solicitado, el sistema responde con un mensaje indicándolo.
