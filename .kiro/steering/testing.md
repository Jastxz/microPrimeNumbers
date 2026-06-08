---
inclusion: fileMatch
fileMatchPattern: "**/*Test.java,**/*Tests.java"
---

# Testing — microPrimeNumbers

## Framework
- JUnit 5 + Spring Boot Test

## Key Test Classes
- `PrimeApplicationTests` — Spring Boot integration tests
- `CallsTest` — Endpoint call integration tests

## Run
- `./mvnw test`

## Conventions
- Test classes in `src/test/java/` mirroring source structure
- Integration tests use `@SpringBootTest`
- Name: `*Test.java` or `*Tests.java`
- Verify both algorithm correctness and HTTP contract
