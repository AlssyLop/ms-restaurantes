# HU-2: Crear restaurante

## Historia de usuario

> **Rol:** Administrador de la plazoleta de comidas
> **Funcionalidad:** Crear en el sistema los restaurantes
> **Motivo:** Brindarle al cliente la posibilidad de escoger en cuál restaurante pedir sus alimentos

---

## Criterios de aceptación

### Campos obligatorios

La creación del restaurante debe solicitar los siguientes campos, todos obligatorios:

| Campo | Tipo | Validaciones |
|---|---|---|
| Nombre | Texto | Puede contener números, pero no se permiten nombres con solo números. Debe ser único en el sistema |
| NIT | Texto | Solo numérico. Debe ser único en el sistema |
| Direccion | Texto | Sin validación de formato específica |
| Telefono | Texto | Máx. 13 caracteres. Debe comenzar con `+` seguido de solo números. Ej: `+573005698325` |
| UrlLogo | Texto | Debe tener formato de URL válida |
| IdPropietario | Numérico | Debe corresponder a un usuario existente con rol PROPIETARIO |

### Reglas de negocio

- Al crear el restaurante, se debe validar que el `IdPropietario` corresponda a un usuario que exista y tenga el rol **PROPIETARIO**.
- Si el `IdPropietario` no existe o el usuario no tiene rol PROPIETARIO, el sistema debe rechazar la creación con un mensaje de error.
- El nombre del restaurante debe ser único (no pueden existir dos restaurantes con el mismo nombre).
- El NIT debe ser único (no pueden existir dos restaurantes con el mismo NIT).
- El administrador debe estar autenticado en el sistema para crear un restaurante.

### Respuestas del sistema

- **Creación exitosa:** El sistema responde con un mensaje de confirmación indicando que el restaurante se creó exitosamente, junto con los datos del restaurante creado.
- **Error de validación:** El sistema responde con mensajes de error indicando qué campo no cumple las validaciones y por qué.
- **Error de propietario inválido:** Si el `IdPropietario` no existe o no tiene rol PROPIETARIO, el sistema responde con un mensaje de error.
- **Error de duplicado:** Si el nombre o el NIT ya existen, el sistema responde con un mensaje de error indicando el conflicto.

---

## Interfaz del sistema

No existe interfaz gráfica (frontend). El administrador interactúa con el sistema a través de servicios REST documentados con OpenAPI (Swagger).

---

## Endpoints (propuesta inicial)

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/restaurantes` | Crear restaurante |

### Request body (JSON)

```json
{
  "nombre": "string",
  "nit": "string",
  "direccion": "string",
  "telefono": "string",
  "urlLogo": "string",
  "idPropietario": 1
}
```

### Response 201 — Creado exitosamente

```json
{
  "mensaje": "Restaurante creado exitosamente"
}
```

### Response 400 — Error de validación

```json
{
  "mensaje": "El nombre no puede contener solo números"
}

{
  "mensaje": "El teléfono debe comenzar con +"
}

{
  "mensaje": "El NIT debe ser numérico"
}
```

### Response 404 — Propietario no encontrado

```json
{
  "mensaje": "El usuario con id 1 no existe o no tiene el rol PROPIETARIO"
}
```

### Response 409 — Conflicto (duplicado)

```json
{
  "mensaje": "El nombre del restaurante ya existe"
}
```

---

## Suposiciones validadas

1. ✅ El administrador envía los datos a través de servicios REST (sin frontend).
2. ✅ Al crear exitosamente, el sistema responde con mensaje de confirmación indicando que se creó el restaurante.
3. ✅ Si hay errores de validación, el sistema responde con mensajes de error indicando el campo incorrecto.
4. ✅ El nombre del restaurante debe ser único. Puede contener números, pero no solo números.
5. ✅ El NIT debe ser único.
6. ✅ El teléfono debe comenzar con `+` seguido de solo números. Máx. 13 caracteres.
7. ✅ El administrador debe estar autenticado.
8. ✅ Si el id del propietario no existe o no es PROPIETARIO, el sistema responde con error.
9. ✅ La URL del logo debe tener formato de URL válida.
10. ✅ La dirección no tiene validación de formato específica.
