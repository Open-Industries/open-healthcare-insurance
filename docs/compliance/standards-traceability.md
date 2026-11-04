# Standards Traceability Matrix

Every standard or framework claimed by this platform and where its evidence lives.
"Aligned to" throughout — no certification or regulatory-compliance claim is made.

| Standard / framework | Area | Evidence |
|---|---|---|
| ISO/IEC/IEEE 42010 | Architecture description | `docs/architecture/architecture-description.md`; `docs/adr/` |
| ISO/IEC 25010 | Quality model | `docs/architecture/quality-attributes.md` (QS-01…QS-08); tests; SLOs |
| ISO/IEC/IEEE 15288 / 12207 | Life-cycle processes | `docs/lifecycle/software-lifecycle.md` |
| ISO/IEC 27001 / 27002 | ISMS controls | `docs/security/` |
| Twelve-Factor App | Cloud methodology | `docs/architecture/twelve-factor.md` |
| OpenAPI 3.1 / AsyncAPI 2.6 / CloudEvents 1.0 | Contracts & events | `contracts/` |
| RFC 9457 | Error format | `Problem.java` in every service |
| HL7 FHIR R4 | Health interoperability resources | `docs/domain/domain-standards.md` resource mapping (Patient/Coverage/Claim posture) |
| ISO 27799 | Health informatics information security | Security architecture specialization |
| HIPAA | US health privacy/security rules | Alignment posture: safeguards mapped; no certification claimed |
| X12 EDI (837/835) | Claims and remittance | Boundary: EDI translation at the edge; core contracts are API-first |
| ICD-10 | Diagnosis classification | Code-system posture for claim categories |
