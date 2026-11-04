# Provider Catalog Service

Bounded context: **Providers** — Contracted provider network catalogue.

Alignment: Provider directory data (FHIR Organization posture). Lifecycle transitions take no body. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8084` (`HTTP_PORT`) |
| Base path | `/api/v1/providers` |
| Owns data | Provider records |

Run locally: `mvn -pl services/provider-catalog-service quarkus:dev`.
