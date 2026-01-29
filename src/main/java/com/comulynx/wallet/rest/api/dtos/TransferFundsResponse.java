package com.comulynx.wallet.rest.api.dtos;

import org.springframework.http.HttpStatus;

public record TransferFundsResponse(
        HttpStatus status,
        String responseMessage
) {
}
