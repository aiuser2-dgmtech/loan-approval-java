# AGENTS.md — loan-approval-service

## Service Overview
This is a retail banking loan approval microservice for Zenith Bank's personal lending platform.
It evaluates loan applications from individual customers based on credit score, annual income,
and employment status. It returns an approval/rejection decision with an applicable interest rate.
This service is consumed by the mobile banking app and the branch portal.

## Technology Stack
- Java 21 (use records for DTOs, pattern matching where it improves clarity)
- Spring Boot 3.2.x — never use deprecated APIs from Spring 5 or Spring Boot 2.x
- Maven — never suggest Gradle alternatives
- H2 in-memory database for dev/test; PostgreSQL in production

## How to Build
```bash
mvn clean compile          # compile only
mvn clean test             # compile + unit tests
mvn clean verify           # full build