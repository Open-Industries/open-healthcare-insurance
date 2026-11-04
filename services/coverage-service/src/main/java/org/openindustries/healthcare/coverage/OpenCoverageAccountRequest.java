package org.openindustries.healthcare.coverage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record OpenCoverageAccountRequest(
        @NotNull java.util.UUID memberId,
        @PositiveOrZero long coverageLimitMinor) {
}
