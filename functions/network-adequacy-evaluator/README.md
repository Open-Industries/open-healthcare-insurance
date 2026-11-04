# Network Adequacy Evaluator (serverless function)

Quarkus Funqy function on Knative (scale to zero). Compares provider-network gap counts and coverage percentage against targets; one finding per breach.

Invoke: `POST /networkAdequacyEvaluator` with the KPI payload.
