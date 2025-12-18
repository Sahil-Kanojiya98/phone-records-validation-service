package com.example.phonerecords.exception;

public class PhoneValidationServiceUnavailableException extends RuntimeException {
    public PhoneValidationServiceUnavailableException(String message) {
        super(message);
    }

    public PhoneValidationServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
