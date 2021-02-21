package com.openhack.toyland.exception;

public class InvalidRequestBodyException extends RuntimeException {

    public InvalidRequestBodyException(String message) {
        super(message);
    }
}
