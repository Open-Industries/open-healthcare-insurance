# Data Architecture

Data view (VP-5) of the Architecture Description.

## 1. Ownership

| Data | Owner (only writer) | Store (reference → production) |
|---|---|---|
| Members records | member-service | in-memory → PostgreSQL `member` |
| Coverage records | coverage-service | in-memory → PostgreSQL `coverage` |
| Adjudication Rules records | adjudication-rules-service | in-memory → PostgreSQL `adjudication` |
| Providers records | provider-catalog-service | in-memory → PostgreSQL `provider` |
| Notifications records | notification-service | in-memory → PostgreSQL `notification` |

In-memory adapters exist so the platform runs anywhere with zero setup; production
persistence is configuration (`DB_KIND`, `DB_URL`) plus the JPA adapter — the domain and
contracts never change (12-Factor IV).

## 2. Privacy and identifiers

Health data is the most sensitive class this program handles: member numbers are validated and never stored or returned in clear (masked only), no clinical data is stored by this core at all, and every record here is synthetic. HIPAA alignment is a posture for adopters' BAAs and safeguards, not a certification. Internal keys are UUIDs. All values in this repository are synthetic.

## 3. Consistency, evidence, retention

Strong within a context; eventual across contexts via CloudEvents with idempotent
consumers. Consumption and decision records are append-only evidence; corrections are
compensating records, never edits. Retention per the adopter's regulatory schedule;
personal data erasable per data-subject rights while evidential records are retained in
pseudonymized form (UUIDs only). Backups per service, encrypted, restore tested
(ISO 27002 A.8.13).
