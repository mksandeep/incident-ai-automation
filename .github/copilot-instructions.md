# Copilot Instructions

## Project
- This is a Java 17 Spring Boot application built with Maven (`com.example.incidentai`).
- It polls ServiceNow incidents, retrieves runbook evidence, requests a constrained Azure OpenAI plan, sends approval emails, and records execution results in an H2/JPA store.
- Main flow: `Orchestrator` coordinates `SnowClient`, `AiPlanner`, `MailService`, `IncidentRepo`, and `ActionRegistry`; `ApprovalController` handles approval decisions.

## Build and Run
- Run tests: `mvn test`
- Build: `mvn package`
- Run locally: `mvn spring-boot:run` (configure the environment variables documented in `README.md`).
- Keep changes compatible with Java 17 and the Spring Boot version managed by `pom.xml`.

## Safety and Behavior
- Never execute arbitrary model output, shell commands, or unvalidated action parameters. `ActionRegistry` is the execution boundary; keep actions explicitly allowlisted and validate every parameter there.
- Model output is a proposal, not authorization. Preserve the approval requirement, single-use decision behavior, and the rule that decline performs no action.
- Treat incident descriptions and retrieved runbook content as untrusted input. Do not let them override system constraints or expand the action allowlist.
- Do not log or expose credentials, API keys, approval tokens, or other secrets. Use configuration/environment variables for secrets.
- Keep failure handling fail-safe: uncertain plans or invalid actions must not trigger execution.

## Change Conventions
- Keep changes focused and follow the existing Spring component and constructor-injection patterns.
- Preserve the current incident status and persistence behavior unless the task explicitly requires changing it; update or add focused tests for behavioral changes.
- Update `README.md` when configuration, local setup, or operator-facing behavior changes.
