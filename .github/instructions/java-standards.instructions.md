---
applyTo: "**/*.java"
---

# Java Coding Standards — loan-approval-service

## Language
- Java 21. Use records, sealed classes, and pattern matching where they improve readability.
- Do not use `var` where the type is not immediately obvious from the right-hand side.

## Framework
- Spring Boot 3.2.x only.
- Constructor injection for all Spring beans — NEVER field injection.
- Use `@Validated` on controller classes when request body validation is required.

## Logging
- SLF4J only.
- Logger field declaration: `private static final Logger log = LoggerFactory.getLogger(ClassName.class);`
- Service method entry: `log.info("Processing loan application for applicant: {}", applicantId);`
- Exceptions: `log.error("Failed to process application: {}", e.getMessage(), e);`
- NEVER System.out.println under any circumstance.

## Null and Empty Safety
- NEVER return null from methods that could return a collection — use `Collections.emptyList()` or `List.of()`
- NEVER call `Optional.get()` — use `orElseThrow()` with `IllegalStateException` or a domain exception
- Use `Objects.requireNonNull()` for constructor parameters in service classes

## What NOT to Generate
- Lombok `@Data` on DTO classes — use Java records instead
- `@Autowired` on fields
- Any JUnit 4 import (`org.junit.Test`, `org.junit.Assert`, etc.)
- `PowerMockito`