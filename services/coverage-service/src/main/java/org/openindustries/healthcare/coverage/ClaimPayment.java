package org.openindustries.healthcare.coverage;

import java.time.Instant;
import java.util.UUID;

/** One guarded consumption record — the auditable evidence of a draw on a CoverageAccount. */
public class ClaimPayment {
    public UUID id;
    public UUID accountId;
    public String claimType;
    public long amountMinor;
    public long remainingAfter;
    public Instant recordedAt;

    public static ClaimPayment of(UUID accountId, String claimType, long amountMinor, long remainingAfter) {
        ClaimPayment r = new ClaimPayment();
        r.id = UUID.randomUUID();
        r.accountId = accountId;
        r.claimType = claimType;
        r.amountMinor = amountMinor;
        r.remainingAfter = remainingAfter;
        r.recordedAt = Instant.now();
        return r;
    }
}
