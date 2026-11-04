# Claim Fraud Scorer (serverless function)

Quarkus Funqy function on Knative (scale to zero). Scores claim risk signals (velocity, mismatch, upcoding indicators) with an auditable rationale.

Invoke: `POST /claimFraudScorer` with the event payload.
