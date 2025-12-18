package com.example.phonerecords.exception;

public class PhoneAlreadyExistsException extends RuntimeException {

    public PhoneAlreadyExistsException() {
        super();
    }

    public PhoneAlreadyExistsException(String message) {
        super(message);
    }
}
