package com.luminor.paymentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String statusText) {
        super(statusText);
    }
}
