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
