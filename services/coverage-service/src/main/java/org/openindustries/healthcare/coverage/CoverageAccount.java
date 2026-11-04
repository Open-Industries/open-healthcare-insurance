package org.openindustries.healthcare.coverage;

import java.util.UUID;

/**
 * CoverageAccount aggregate — the guarded resource account of this core: a balance/capacity that
 * consumption operations draw from. Core invariant (ADR-0004): it NEVER goes negative;
 * an operation that would overdraw it is rejected and records nothing.
 */
public class CoverageAccount {
    public UUID id;
        public java.util.UUID memberId;
    public long coverageLimitMinor;
    public String status;

    public static CoverageAccount open(java.util.UUID memberId, long coverageLimitMinor) {
        CoverageAccount a = new CoverageAccount();
        a.id = UUID.randomUUID();
        a.memberId = memberId;
        a.coverageLimitMinor = coverageLimitMinor;
        a.status = "ACTIVE";
        return a;
    }
}
