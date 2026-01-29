package com.comulynx.wallet.rest.api.dtos;

public record GetAccountRequest(
        String customerId, String accountNo
) {
}
