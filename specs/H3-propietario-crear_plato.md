# HU-3: Crear plato

## Historia de usuario

> **Rol:** Propietario de un restaurante
> **Funcionalidad:** Crear platos para asociarlos al menú de mi restaurante
> **Motivo:** Brindar diferentes opciones de platos al cliente

---

## Criterios de aceptación

### Campos obligatorios

La creación del plato debe solicitar los siguientes campos, todos obligatorios:

| Campo | Tipo | Validaciones |
|---|---|---|
| Nombre del plato | Texto | Debe ser único dentro del mismo restaurante |
| Precio | Numérico entero | Positivo, mayor a 0, solo numérico |
| Descripcion | Texto | Sin validación de formato específica |
| UrlImagen | Texto | Debe tener formato de URL válida |
| Categoria | Texto | Sin una lista predefinida, se escribe como texto libre |

### Reglas de negocio

- Solo el propietario del restaurante puede crear platos.
- El plato se asocia automáticamente al restaurante del propietario autenticado (según su rol y usuario).
- Por defecto, cada plato recién creado tiene la variable `activo = true`.
- El propietario debe estar autenticado y tener el rol **PROPIETARIO**.
- El nombre del plato debe ser único dentro del mismo restaurante.

### Validación campo por campo

El sistema valida cada campo de forma secuencial y se **detiene en el primero que falle**, devolviendo un solo error por respuesta.

### Respuestas del sistema

- **Creación exitosa:** El sistema responde únicamente con un mensaje de confirmación (sin datos del plato).
- **Error de validación:** El sistema responde con el mensaje del primer campo que no cumpla las validaciones.

---

## Interfaz del sistema

No existe interfaz gráfica (frontend). El propietario interactúa con el sistema a través de servicios REST documentados con OpenAPI (Swagger).

---

## Endpoints (propuesta inicial)

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/platos` | Crear plato |

### Request body (JSON)

```json
{
  "nombre": "string",
  "precio": 1,
  "descripcion": "string",
  "urlImagen": "string",
  "categoria": "string"
}
```

### Response 201 — Creado exitosamente

```json
{
  "mensaje": "Plato creado exitosamente"
}
```

### Response 400 — Error de validación (campo requerido)

```json
{
  "nombre": "El nombre del plato es requerido"
}
```

```json
{
  "precio": "El precio es requerido"
}
```

```json
{
  "descripcion": "La descripción es requerida"
}
```

```json
{
  "urlImagen": "La URL de la imagen es requerida"
}
```

```json
{
  "categoria": "La categoría es requerida"
}
```

### Response 400 — Error de validación (formato inválido)

```json
{
  "precio": "El precio debe ser un número entero positivo mayor a 0"
}
```

```json
{
  "urlImagen": "La URL de la imagen no tiene un formato válido"
}
```

---

## Suposiciones validadas

1. ✅ El propietario envía los datos del plato a través de servicios REST (sin frontend).
2. ✅ Al crear exitosamente, el sistema responde solo con el mensaje de confirmación (sin datos del plato).
3. ✅ Si hay errores de validación, el sistema responde con el error del primer campo que falle (validación secuencial).
4. ✅ El propietario debe estar autenticado y tener el rol PROPIETARIO.
5. ✅ El plato se asocia automáticamente al restaurante del propietario autenticado según su rol.
6. ✅ El nombre del plato debe ser único dentro del mismo restaurante.
7. ✅ La URL de la imagen debe tener formato de URL válida.
8. ✅ La categoría se escribe como texto libre, sin lista predefinida.
9. ✅ El precio debe ser un número entero, positivo y mayor a cero.
10. ✅ La descripción no tiene validación de formato específica.
