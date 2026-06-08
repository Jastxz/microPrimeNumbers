---
inclusion: always
---

# Coding Standards — microPrimeNumbers

## Java / Spring Boot
- Spring Boot 3.x conventions
- Layered: `@RestController` → `@Service` → algorithm logic
- Constructor injection only
- Math algorithms as pure static methods or dedicated classes
- DTOs for API responses

## Build & Run
- `./mvnw clean package`
- `./mvnw spring-boot:run`
- Depends on eurekaServer for registration
