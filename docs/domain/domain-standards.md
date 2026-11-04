# Domain Standards — Healthcare Insurance

Alignment mapping for this core. *Aligned to* throughout: this repository processes **no
real health data** and claims no HIPAA, HITRUST or regulatory certification.

## Concept mapping

| Platform concept | Domain standard | Mapping |
|---|---|---|
| Member | HL7 FHIR `Patient` (posture) | Registry shapes follow FHIR naming discipline; identifiers masked beyond FHIR norms for a public reference |
| Coverage account | HL7 FHIR `Coverage` (posture) | Guarded limit accounting; the no-overrun invariant |
| Claim payments | HL7 FHIR `Claim`/`ClaimResponse` posture; X12 837/835 at the boundary | Payment records by claim type |
| Adjudication | Payer policy | Rules context with reasoned ALLOWED/DEGRADED/DENIED decisions |
| Security | ISO 27799 + HIPAA safeguard posture | `docs/security/` specialization; minimum-necessary as a design rule |
| Diagnoses | ICD-10 | Category posture only; no clinical data is stored |

## Scope boundaries

No clinical records, no eligibility verification against government programs, no real
PHI — ever — in this repository. Adopters integrate certified EDI translators and their
own compliance programs at the boundary.
