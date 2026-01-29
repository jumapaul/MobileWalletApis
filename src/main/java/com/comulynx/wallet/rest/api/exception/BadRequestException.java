package com.comulynx.wallet.rest.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadRequestException(String message) {
        super(message);
    }
}
