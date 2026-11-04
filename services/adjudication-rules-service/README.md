# Adjudication Rules Service

Bounded context: **Adjudication Rules** — Decision rules binding claim types to amount and processing envelopes, with reasons.

Alignment: Payer adjudication policy as auditable rules. Decisions are `ALLOWED` / `DEGRADED` / `DENIED`, always
with the matched rule and a human-readable reason for audit. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8083` (`HTTP_PORT`) |
| Base path | `/api/v1/adjudication-rules` |
| Owns data | AdjudicationRule rules |
| Publishes | `adjudication.activated` |

Run locally: `mvn -pl services/adjudication-rules-service quarkus:dev`.
