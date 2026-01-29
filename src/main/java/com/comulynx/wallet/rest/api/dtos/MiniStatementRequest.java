package com.comulynx.wallet.rest.api.dtos;

import jakarta.validation.constraints.NotBlank;

public record MiniStatementRequest(
        @NotBlank(message = "Customer id is required")
        String customerId,
        @NotBlank(message = "Account number is required")
        String accountNumber,
        String pin
) {
}
