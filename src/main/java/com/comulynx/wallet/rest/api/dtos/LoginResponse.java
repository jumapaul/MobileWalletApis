package com.comulynx.wallet.rest.api.dtos;

public record LoginResponse(
        String customerName,
        String customerId,
        String customerEmail,
        String customerAccount
) {
}
