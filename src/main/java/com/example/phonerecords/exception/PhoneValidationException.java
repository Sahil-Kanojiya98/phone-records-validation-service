package com.example.phonerecords.exception;

public class PhoneValidationException extends RuntimeException {

    public PhoneValidationException() {
        super();
    }

    public PhoneValidationException(String message) {
        super(message);
    }
}
