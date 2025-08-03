# microPrimeNumbers

Un microservicio Spring Boot que proporciona herramientas computacionales para trabajar con números primos.

## 📋 Descripción

Este microservicio ofrece dos endpoints principales para realizar cálculos con números primos:
- **Diferencias entre primos**: Calcula las diferencias consecutivas entre números primos
- **Coordenadas polares**: Genera coordenadas polares basadas en números primos

## 🚀 Características

- **Rate Limiting**: Control de límites por usuario (2 req/10s) y global (15 req/10s)
- **Validación robusta**: Manejo de errores 400, 429, 500, 503
- **Base de datos H2**: Almacenamiento en memoria para tracking de requests
- **Optimización**: Algoritmo de Criba de Eratóstenes eficiente
- **Tests de rendimiento**: Suite completa de pruebas de carga

## 🛠️ Tecnologías

- **Java 21**
- **Spring Boot 3.5.3**
- **Spring Data JPA**
- **H2 Database** (en memoria)
- **Lombok**
- **Maven**

## 📦 Instalación

### Prerrequisitos
- Java 21 o superior
- Maven 3.6+

### Ejecutar la aplicación

```bash
# Clonar el repositorio
git clone https://github.com/usuario/microPrimeNumbers.git
cd microPrimeNumbers

# Compilar y ejecutar
./mvnw spring-boot:run

# O usando Maven
mvn spring-boot:run
```
También puedes usar el botón play de cualquier IDE para Java con el que te manejes.


La aplicación estará disponible en `http://localhost:8080`

## 📖 API Reference

### POST `/v0/diff` - Diferencias entre primos

Calcula las diferencias consecutivas entre los primeros N números primos.

**Request:**
```json
{
  "number": 100
}
```

**Response:**
```json
{
  "primos": [2, 3, 5, 7, 11, 13, ...],
  "diferencias": [1, 2, 2, 4, 2, ...],
  "mensaje": "1:1;2:8;4:2;6:2;..."
}
```

### POST `/v0/polar` - Coordenadas polares

Genera coordenadas polares basadas en los primeros N números primos.

**Request:**
```json
{
  "number": 50
}
```

**Response:**
```json
{
  "puntos": [
    {"axisX": 2, "axisY": 2},
    {"axisX": 3, "axisY": 3},
    {"axisX": 5, "axisY": 5}
  ]
}
```

## ⚡ Límites y Restricciones

| Parámetro | Límite |
|-----------|--------|
| **Número máximo** | 500,000 |
| **Rate limit por usuario** | 2 requests/10 segundos |
| **Rate limit global** | 15 requests/10 segundos |
| **Tiempo de respuesta objetivo** | < 500ms |

## 🧪 Testing

### Tests de rendimiento

```bash
# Ejecutar tests de rendimiento
mvn test -Dtest=PrimeApplicationTests

# Ejecutar desde main
java -cp target/classes es.jastxz.microPrimeNumbers.PrimeApplicationTests
```
También puedes usar el botón play de cualquier IDE para Java con el que te manejes.

## 📊 Códigos de Estado

| Código | Descripción |
|--------|-------------|
| **200** | Operación exitosa |
| **400** | Petición inválida (number = 0) |
| **429** | Rate limit excedido |
| **500** | Error interno del servidor |
| **503** | Límite computacional excedido |

## 🏗️ Estructura del Proyecto

```
src/
├── main/
│   ├── java/es/jastxz/microPrimeNumbers/
│   │   ├── components/     # Filtros (Rate limiting)
│   │   ├── controller/     # REST Controllers
│   │   ├── model/         # DTOs y entidades
│   │   ├── repo/          # Repositorios JPA
│   │   ├── service/       # Lógica de negocio
│   │   └── util/          # Utilidades (Criba de Eratóstenes)
│   └── resources/
│       ├── api/           # Especificación OpenAPI
│       └── application.yml
└── test/
    └── java/              # Tests de rendimiento
```

## 🔧 Configuración

### application.yml

```yaml
spring:
  application:
    name: microPrimeNumbers
  datasource:
    url: jdbc:h2:mem:ratelimitdb
    username: sa
    password: ""
  jpa:
    hibernate:
      ddl-auto: update
  h2:
    console:
      enabled: true
```

### Variables de entorno

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `MAX_NUMBER_LIMIT` | Límite máximo de cálculo | 500000 |
| `MAX_REQUESTS_USER_PER_MINUTE` | Rate limit por usuario | 2 |
| `MAX_REQUESTS_GLOBAL_PER_MINUTE` | Rate limit global | 15 |

## 📈 Rendimiento

### Benchmarks típicos (Intel i5-8250U)

| Número | Tiempo (ms) | Primos encontrados |
|--------|-------------|-------------------|
| 1,000 | ~0.4ms | 168 |
| 10,000 | ~3ms | 1,229 |
| 100,000 | ~15ms | 9,592 |
| 500,000 | ~75ms | 41,538 |

## 🐛 Troubleshooting

### Errores comunes

**Error: "No default constructor for entity"**
```bash
# Solución: Verificar que las entidades tengan constructor sin parámetros
```

**Error: "Rate limit exceeded"**
```bash
# Esperar 10 segundos o usar diferentes IPs
```

**Error: "Service Unavailable"**
```bash
# Reducir el número límite para primos en la petición (< 500,000)
```

## 🚀 Desarrollo

### Agregar nuevos endpoints

1. Crear nuevo controller en `controller/`
2. Implementar service en `service/impl/`
3. Agregar validaciones en el controller
4. Actualizar `api.yml` con la especificación

### Modificar límites

Editar constantes en:
- `Util.MAX_NUMBER_LIMIT` (límite computacional)
- `RequestsLimitFilter` (rate limiting)

## 📝 TODO

- Implementar cache para resultados
- Agregar más algoritmos de números primos

## 📄 Licencia

Este proyecto está bajo la Licencia GPL v3. Ver [LICENSE](LICENSE) para más detalles.

## 📧 Contacto

**Desarrollador**: Jastxz(javicraft14@gmail.com)
**Proyecto**: [microPrimeNumbers](https://github.com/usuario/microPrimeNumbers)