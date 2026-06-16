# AGENTS.md — loan-approval-java (root)

## Repository
This is a single-service repository: a Spring Boot loan approval API.
This file contains repository-wide defaults.

### AGENTS.md Hierarchy
When working with any file:
- Identify all AGENTS.md files that apply to that file.
- Apply instructions from the repository root AGENTS.md first.
- Apply instructions from nested AGENTS.md files next.
- If instructions conflict, the MOST SPECIFIC (nearest) AGENTS.md takes precedence.
- A nested AGENTS.md may override, replace, or narrow rules from parent AGENTS.md files.

When answering questions about a specific file, ALWAYS resolve instructions
using the nearest applicable AGENTS.md.

## Universal Rules
- NEVER commit secrets, API keys, or credentials
- NEVER log PII: applicant names, PAN numbers, mobile numbers, full credit scores
  (credit score BANDS like "600-749" are fine in logs; exact scores are not)
- Log business events at INFO level.
- Git commits follow Conventional Commits: feat:, fix:, chore:, docs:, refactor:

## Build Commands
mvn clean compile
mvn clean test
mvn clean verify

## Architecture Rules
- Controller: REST only, zero business logic
- Service: all business rules, stateless
- Strategy pattern for loan decision logic (see com.bank.loans.service.strategies)
- Domain: plain Java objects, zero Spring annotations

## Coding Standards
- SLF4J only. NEVER System.out.println
- Constructor injection only. NEVER @Autowired on fields
- NEVER return null for collections
- NEVER Optional.get() — use orElseThrow()
- All public methods: Javadoc
- Decimal: ALWAYS Decimal/BigDecimal with String constructor — new BigDecimal("7.5")

## Domain Glossary
- Applicant: customer applying for a loan (applicantId = UUID string)
- Credit Score: 300-900 CIBIL scale. Below 600 = high risk.
- Employment Status: SALARIED | SELF_EMPLOYED | UNEMPLOYED | RETIRED

## Where to Find More
- src/main/java/com.bank/loans/service/AGENTS.md — service-specific stack, build commands, conventions, overrides 