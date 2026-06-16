# AGENTS.md — service package (overrides/additions)

This file applies to code in com.bank.loans.service and below.
Root AGENTS.md rules still apply — this file adds service-layer specifics
and ONE explicit override.

## EXPLICIT OVERRIDE: Logging Level for Loan Rejections

This section overrides the root logging rule for loan decision outcomes.

### Effective Rules
- APPROVED loan decisions MUST be logged at INFO level.
- REJECTED loan decisions MUST be logged at WARN level.
- REJECTED loan decisions MUST NOT be logged only at INFO level.
- When a loan is rejected, include the rejection reason in the WARN log message.

Example:
```java
if (decision.status().equals("REJECTED")) {
    log.warn("Loan rejected for applicant {}: {}", applicantId, decision.reason());
} else {
    log.info("Loan decision for applicant {}: {}", applicantId, decision.status());
}