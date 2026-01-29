package com.comulynx.wallet.rest.api.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateCustomerRequest(
        String pin,
        String firstName,
        String lastName,
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank
        String customerId
) {
}
