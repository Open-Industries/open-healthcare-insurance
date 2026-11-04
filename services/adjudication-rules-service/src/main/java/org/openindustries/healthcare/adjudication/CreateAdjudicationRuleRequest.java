package org.openindustries.healthcare.adjudication;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateAdjudicationRuleRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "INPATIENT|OUTPATIENT|PHARMACY|DENTAL") String claimType,
        @Positive long maxAmountMinor,
        @Positive int maxProcessingDays,
        @Min(1) @Max(100) int priority) {
}
