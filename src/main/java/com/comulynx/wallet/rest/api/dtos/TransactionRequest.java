package com.comulynx.wallet.rest.api.dtos;

public record TransactionRequest(
        String pin,
        String customerId
) {
}
