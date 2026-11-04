# Member Service

Bounded context: **Members** — Member registry; the member number is masked at enrolment and never kept in clear.

Alignment: HL7 FHIR Patient posture; ISO 27799 minimum-necessary. Lifecycle transitions take no body; secret identifiers,
where the domain has them, are masked at creation and never stored or returned in clear.
All data is synthetic.

| Item | Value |
|---|---|
| Port | `8081` (`HTTP_PORT`) |
| Base path | `/api/v1/members` |
| Owns data | Member records |
| Publishes | `member.created`, lifecycle events (see `contracts/asyncapi.yaml`) |

Run locally: `mvn -pl services/member-service quarkus:dev`.
