package org.openindustries.healthcare.adjudication;

import java.util.UUID;

/**
 * AdjudicationRule aggregate — a decision rule of this core: for one claimType, it guarantees a
 * capacity (maxAmountMinor) and a threshold (maxProcessingDays), with a precedence priority.
 * Only ACTIVE rules decide; every decision names its rule and its reason (audit).
 */
public class AdjudicationRule {
    public UUID id;
    public String name;
    public String claimType;
    public long maxAmountMinor;
    public int maxProcessingDays;
    public int priority;
    public String status;

    public static AdjudicationRule create(String name, String claimType, long maxAmountMinor, int maxProcessingDays, int priority) {
        AdjudicationRule r = new AdjudicationRule();
        r.id = UUID.randomUUID();
        r.name = name;
        r.claimType = claimType;
        r.maxAmountMinor = maxAmountMinor;
        r.maxProcessingDays = maxProcessingDays;
        r.priority = priority;
        r.status = "DRAFT";
        return r;
    }
}
