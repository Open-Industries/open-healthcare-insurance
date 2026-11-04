package org.openindustries.healthcare.adjudication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record EvaluateRequest(
        @NotBlank @Pattern(regexp = "INPATIENT|OUTPATIENT|PHARMACY|DENTAL") String claimType,
        @Positive int requiredMaxProcessingDays,
        @Positive long requiredMaxAmountMinor) {
}
