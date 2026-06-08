---
inclusion: always
---

# Architecture — microPrimeNumbers

## Stack
- **Framework:** Spring Boot + Spring Cloud (Eureka client)
- **Language:** Java
- **Build:** Maven
- **Domain:** Prime number computation as a microservice

## Module Boundaries (from graphify: 142 nodes, 235 edges, 20 communities)
- God nodes: `RequestsLimitFilter` (9 edges), `Util` (9), `PrimeApplicationTests` (8), `CallsTest` (7), `PrimesController` (6)
- REST API for prime number algorithms (generation, testing, factorization)
- `RequestsLimitFilter` — Rate limiting (shared microservices pattern)
- `PrimesController` — REST endpoints for prime operations
- `Util` — Shared utility functions for prime computations
- Registered with Eureka service discovery
- Has integration tests (`PrimeApplicationTests`, `CallsTest`)

## Dependency Rules
- Controller (`PrimesController`) → Service → algorithm implementations (layered)
- `Util` is a shared utility — keep it stateless
- `RequestsLimitFilter` applied globally
- Math/algorithm code must be pure — no Spring dependencies
- Endpoints expose results as JSON DTOs

## Ecosystem
Part of cluster: eurekaServer, microGateway, microNeural, microTracking, micro-adversarial-search
