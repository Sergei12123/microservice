package com.example.microservice.exception;

import com.example.microservice.exception.enums.IncorrectBearerType;

public class IncorrectBearerException extends RuntimeException {

    private static final String WRONG_BEARER="Entered bearer is %s";

    public IncorrectBearerException(IncorrectBearerType cause) {
        super(String.format(WRONG_BEARER, cause.name().toLowerCase()));
    }
}
