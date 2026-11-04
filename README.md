# Open Healthcare Insurance — Reference Health Coverage Core Platform

A **standard, generic healthcare insurance core platform** that any organization in this vertical
can deploy and adapt: built as **microservices and serverless functions** on
**Java 21 + Quarkus**, aligned with **The Twelve-Factor App**, documented under the ISO
architecture and quality standards, and aligned with the domain standards of the
vertical (see the traceability matrix). Everything is *aligned to* the cited standards —
**no certification, conformance or regulatory-compliance claim is made**. All data is
**synthetic**.

> Reference implementation: small, complete and runnable. The rules that matter in this
> domain are enforced in code and covered by tests — start with the invariant:
> claim payments against a member's coverage account never exceed the coverage limit — a payment that would overrun it is rejected (422) and records nothing.

## Standards alignment

| Standard / framework | What it governs here | Where |
|---|---|---|
| ISO/IEC/IEEE **42010** | Architecture description | `docs/architecture/architecture-description.md` |
| ISO/IEC **25010** (SQuaRE) | Product quality model and scenarios | `docs/architecture/quality-attributes.md` |
| ISO/IEC/IEEE **15288 & 12207** | Life-cycle processes | `docs/lifecycle/software-lifecycle.md` |
| ISO/IEC **27001 & 27002** | Information security | `docs/security/` |
| **The Twelve-Factor App** | Cloud-native methodology | `docs/architecture/twelve-factor.md` |
| HL7 FHIR R4 | Health interoperability resources | `docs/domain/domain-standards.md` resource mapping (Patient/Coverage/Claim posture) |
| ISO 27799 | Health informatics information security | Security architecture specialization |
| HIPAA | US health privacy/security rules | Alignment posture: safeguards mapped; no certification claimed |
| X12 EDI (837/835) | Claims and remittance | Boundary: EDI translation at the edge; core contracts are API-first |
| ICD-10 | Diagnosis classification | Code-system posture for claim categories |

Full traceability: `docs/compliance/standards-traceability.md`.

## The platform at a glance

### Microservices (Quarkus, `services/`)

| Service | Port | Alignment | Responsibility |
|---|---|---|---|
| `member-service` | 8081 | HL7 FHIR Patient posture; ISO 27799 minimum-necessary | Member registry; the member number is masked at enrolment and never kept in clear. |
| `coverage-service` | 8082 | HL7 FHIR Coverage posture | Guarded coverage-limit accounts per member and the claim payments against them. |
| `adjudication-rules-service` | 8083 | Payer adjudication policy as auditable rules | Decision rules binding claim types to amount and processing envelopes, with reasons. |
| `provider-catalog-service` | 8084 | Provider directory data (FHIR Organization posture) | Contracted provider network catalogue. |
| `notification-service` | 8085 | Platform support (shared pattern) | Channel-agnostic dispatch of domain notifications from events. |

### Serverless functions (Quarkus Funqy + Knative, `functions/`)

| Function | Trigger | Responsibility |
|---|---|---|
| `claim-fraud-scorer` | Event / HTTP | Scores claim risk signals (velocity, mismatch, upcoding indicators) with an auditable rationale. |
| `network-adequacy-evaluator` | Event / HTTP | Compares provider-network gap counts and coverage percentage against targets; one finding per breach. |

## Design principles

1. **One bounded context per service**, identical hexagonal layout everywhere.
2. **The domain invariant is code, not prose** — claim payments against a member's coverage account never exceed the coverage limit — a payment that would overrun it is rejected (422) and records nothing.
3. **Privacy by design** — Health data is the most sensitive class this program handles: member numbers are validated and never stored or returned in clear (masked only), no clinical data is stored by this core at all, and every record here is synthetic. HIPAA alignment is a posture for adopters' BAAs and safeguards, not a certification.
4. **Database per service, API-first (OpenAPI), events as CloudEvents.**
5. **Adapt by configuration** — rules, thresholds, tariffs/rates and providers are data
   and configuration, not code forks (see *Adaptation guide*).

## Quick start

Prerequisites: Java 21, Maven 3.9+, optionally Docker/Podman and Knative/OpenShift.

```bash
mvn verify                                    # build and test everything
mvn -pl services/member-service quarkus:dev
docker compose up -d                          # local PostgreSQL, Kafka, Keycloak
```

## Deployment

- **Kubernetes / OpenShift**: `kubectl apply -k deploy/kubernetes/`.
- **Serverless**: Knative Services in `deploy/knative/` (scale to zero).
- **Local**: `docker-compose.yml` for dependencies; services run with `quarkus:dev`.

## Adaptation guide

| You want to… | Change only… |
|---|---|
| Adjust domain rules and thresholds | Rule data in the `adjudication-rules-service` context and function configuration |
| Change the guarded capacity semantics | The `coverage-service` context (invariant stays: never negative) |
| Plug your IdP / providers | OIDC at the gateway; provider adapters at the boundary |
| Replace in-memory persistence | The repository port's JPA adapter on the service's own database (`DB_KIND`, `DB_URL`) |

## Documentation map

- Architecture (42010): `docs/architecture/` · Decisions: `docs/adr/`
- Domain standards: `docs/domain/domain-standards.md`
- Quality (25010), Security (27001/27002), Life cycle (15288/12207), API, Data, Operations: under `docs/`

## License

Apache-2.0 — see `LICENSE`.
