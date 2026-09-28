# Runbook
## claims-api unavailable
If claims-api is unavailable or returns 503, use RESTART_SERVICE with service=claims-api. Verify health, restart once through approved automation, validate, record evidence.
## provider-api unavailable
If provider-api is unavailable, use RESTART_SERVICE with service=provider-api. Verify, restart once, validate, escalate on failure.
## stale provider cache
If provider data is stale and cache staleness is explicit, use CLEAR_CACHE with cache=provider-search. Capture evidence, clear, validate, record.
