# Spring E-commerce API

API REST de **e-commerce** con **Spring Boot**, **Spring Security con JWT**, **PostgreSQL** y **RabbitMQ**.

## Funcionalidades
- Registro e inicio de sesión (`/auth/register`, `/auth/login`)
- Catálogo de productos con paginación (`/products`)
- Carrito de compra (`/cart`)
- Checkout y pedidos (`/checkout`, `/orders`)
- Publicación de eventos con RabbitMQ tras la compra

## Estructura
Paquetes por módulo: `product`, `user`, `checkout`, `config` (seguridad, JWT, RabbitMQ), `filters` (filtro JWT).

Ejemplos de peticiones en `src/main/resources/httprequests/`.

## Cómo ejecutar
1. Levanta los servicios:
   ```bash
   docker compose up -d
   ```
2. Ejecuta la aplicación:
   ```bash
   ./gradlew bootRun
   ```

La conexión a PostgreSQL está en `src/main/resources/application.properties`.


## Deuda técnica y limitaciones conocidas

- **Secretos en el repositorio (prioridad alta):** `src/main/resources/application.properties` contiene la contraseña de la base de datos y el secreto JWT en texto plano. Hay que rotarlos y moverlos a variables de entorno. Mientras tanto, considera ese secreto como comprometido.
- **Pruebas:** solo existe `EcommerceApiApplicationTests` (carga de contexto). No hay pruebas de servicios, controladores ni de reglas de seguridad.
- **Codificación de la clave JWT:** `JwtService` usa `secret.getBytes()` sin indicar charset, y el nombre del método `getSinginKey` tiene una errata.
- **Ejemplos HTTP:** `requests.http` contiene contraseñas de ejemplo. Son solo para desarrollo local.
