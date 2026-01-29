package com.comulynx.wallet.rest.api.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        String customerId,
        @NotBlank
        String pin
) {
}
