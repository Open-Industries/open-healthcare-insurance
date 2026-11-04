package org.openindustries.healthcare.coverage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateClaimPaymentRequest(
        @NotBlank @Pattern(regexp = "INPATIENT|OUTPATIENT|PHARMACY|DENTAL") String claimType,
        @Positive long amountMinor) {
}
