package org.openindustries.healthcare.provider;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateProviderRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "HOSPITAL|CLINIC|PHARMACY|LAB") String providerType,
        @NotBlank String regionCode) {
}
