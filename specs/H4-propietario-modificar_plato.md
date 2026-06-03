# HU-4: Modificar plato

## Historia de usuario

> **Rol:** Propietario de un restaurante
> **Funcionalidad:** Actualizar la información de los platos en el menú
> **Motivo:** Corregir valores errados o actualizar precios

---

## Criterios de aceptación

### Campos modificables

| Campo | Tipo | Validaciones |
|---|---|---|
| Precio | Numérico entero | Positivo, mayor a 0, solo numérico |
| Descripcion | Texto | Sin validación de formato específica |

Solo se puede modificar **precio** y **descripción**. El nombre, urlImagen, categoría y activo no se pueden modificar.

### Reglas de negocio

- Solo el propietario del restaurante puede modificar platos de su propio restaurante.
- El propietario debe estar autenticado y tener el rol **PROPIETARIO**.
- Ambos campos (precio y descripción) son opcionales en la petición. El propietario puede enviar solo uno o ambos.
- El precio mantiene las mismas validaciones de la creación del plato: entero positivo mayor a 0.

### Validación campo por campo

El sistema valida cada campo de forma secuencial y se detiene en el primero que falle, devolviendo un solo error por respuesta.

### Respuestas del sistema

- **Modificación exitosa:** El sistema responde con un mensaje de confirmación.
- **Error de validación:** El sistema responde con el mensaje del primer campo que no cumpla las validaciones.

---

## Interfaz del sistema

No existe interfaz gráfica (frontend). El propietario interactúa a través de servicios REST documentados con OpenAPI (Swagger).

---

## Endpoints (propuesta inicial)

| Método | Ruta | Descripción |
|---|---|---|
| `PUT` | `/platos/{id}` | Modificar plato |

### Request body (JSON)

```json
{
  "precio": 1,
  "descripcion": "string"
}
```

### Response 200 — Modificado exitosamente

```json
{
  "mensaje": "Plato modificado exitosamente"
}
```

### Response 400 — Error de validación

```json
{
  "precio": "El precio debe ser un número entero positivo mayor a 0"
}
```

### Response 403 — No autorizado

```json
{
  "mensaje": "No tienes permiso para modificar este plato"
}
```

### Response 404 — No encontrado

```json
{
  "mensaje": "El plato con id {id} no existe"
}
```

---

## Suposiciones validadas

1. ✅ El propietario envía los datos a través de servicios REST (sin frontend).
2. ✅ Al modificar exitosamente, el sistema responde con un mensaje de confirmación.
3. ✅ Si hay errores de validación, el sistema responde con el error del primer campo que falle.
4. ✅ El propietario debe estar autenticado y tener el rol PROPIETARIO.
5. ✅ El propietario solo puede modificar platos de su propio restaurante.
6. ✅ El plato se identifica por su ID en la URL.
7. ✅ Solo se pueden modificar precio y descripción.
8. ✅ Ambos campos son opcionales (se puede enviar uno o ambos).
9. ✅ El precio mantiene las mismas validaciones: entero positivo mayor a 0.
