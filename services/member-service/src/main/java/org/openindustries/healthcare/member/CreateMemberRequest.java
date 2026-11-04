package org.openindustries.healthcare.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateMemberRequest(
        @NotBlank String fullName,
        @NotBlank String memberNumber,
        @NotBlank String planCode) {
}
