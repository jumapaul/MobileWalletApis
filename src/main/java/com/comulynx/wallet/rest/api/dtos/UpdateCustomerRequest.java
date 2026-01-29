package com.comulynx.wallet.rest.api.dtos;

public record UpdateCustomerRequest(
        String firstName,
        String customerId
) {
}
