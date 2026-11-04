# Services

One independently deployable Quarkus microservice per bounded context. Every service
follows the same hexagonal layout, exposes `/openapi`, `/q/health` and `/q/metrics`,
returns RFC 9457 problem details, and is configured only through environment variables
(Twelve-Factor). Lifecycle transitions take no body; `@Consumes` appears only on
endpoints that read one.

**Implementation order** (see `../docs/implementation-roadmap.md`):
Members → Coverage → Adjudication Rules → Open APIs.

| Service | Port | Alignment |
|---|---|---|
| `member-service` | 8081 | HL7 FHIR Patient posture; ISO 27799 minimum-necessary |
| `coverage-service` | 8082 | HL7 FHIR Coverage posture |
| `adjudication-rules-service` | 8083 | Payer adjudication policy as auditable rules |
| `provider-catalog-service` | 8084 | Provider directory data (FHIR Organization posture) |
| `notification-service` | 8085 | Platform support (shared pattern) |

Run the pilot service:

```bash
mvn -pl services/member-service quarkus:dev
# OpenAPI UI: http://localhost:8081/docs — health: http://localhost:8081/q/health
```
