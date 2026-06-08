---
inclusion: fileMatch
fileMatchPattern: ".woodpecker*,Dockerfile*,docker-compose*,application.yml"
---

# Deployment — microPrimeNumbers

## CI/CD
- Woodpecker CI pipeline (`.woodpecker.yaml`)

## Local
- `./mvnw spring-boot:run`
- Requires eurekaServer on port 8761

## Startup Order
1. eurekaServer
2. microGateway
3. This service
