package com.comulynx.wallet.rest.api.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

public record TransferMoneyRequest(
        @NotBlank(message = "Customer id is required")
        String customerId,
        @NotBlank(message = "account from is required")
        String accountFrom,
        @NotBlank(message = "account to is required")
        String accountTo,
        @DecimalMin(value = "1.0", message = "The minimum amount is 1.0")
        double amount
) {
}
