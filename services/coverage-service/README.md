# Coverage Service

Bounded context: **Coverage** — Guarded coverage-limit accounts per member and the claim payments against them.

Alignment: HL7 FHIR Coverage posture. Core invariant, enforced in code and tests: **the guarded
balance/capacity never goes negative** — over-drawing operations are rejected with `422`
and nothing is recorded. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8082` (`HTTP_PORT`) |
| Base path | `/api/v1/coverage` |
| Owns data | CoverageAccount accounts, ClaimPayment records |
| Publishes | `coverage.consumed`, `coverage.insufficient` |

Run locally: `mvn -pl services/coverage-service quarkus:dev`.
