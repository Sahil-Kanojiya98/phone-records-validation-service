package com.example.phonerecords.exception;

public class PhoneNotFoundException extends RuntimeException{

    public PhoneNotFoundException() {
        super();
    }

    public PhoneNotFoundException(String message) {
        super(message);
    }
}
