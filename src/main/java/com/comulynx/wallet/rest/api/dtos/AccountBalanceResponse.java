package com.comulynx.wallet.rest.api.dtos;

public record AccountBalanceResponse(
        Double balance,
        String accountNumber
) {
}
