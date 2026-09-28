# Incident AI Automation
Polls ServiceNow incidents for `ABC_GROUP`, retrieves relevant runbook text, asks Azure OpenAI for a constrained JSON plan, emails single-use approval links, and executes only compiled allowlisted actions after approval. Decline performs no action.

## Run
Set `SERVICENOW_BASE_URL`, `SERVICENOW_USERNAME`, `SERVICENOW_PASSWORD`, `AZURE_OPENAI_ENDPOINT`, `AZURE_OPENAI_API_KEY`, `AZURE_OPENAI_DEPLOYMENT`, `SMTP_USERNAME`, `SMTP_PASSWORD`, `APPROVAL_FROM`, `APPROVAL_TO`, `APP_BASE_URL`; then run `mvn spring-boot:run`.

## Production changes
Use ServiceNow OAuth and a secrets manager; protect approval URLs with corporate SSO and obtain approver identity from the authenticated principal; replace simulated executors with idempotent internal automation APIs; add retries, metrics, audit retention, CSRF protection, and integration tests. Never let the LLM emit or execute arbitrary shell commands.
