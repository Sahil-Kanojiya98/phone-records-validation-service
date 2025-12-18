package com.example.phonerecords.dto;

public class PhoneRecordResponse {

    private Long id;
    private String name;
    private String phoneNumber;

    public PhoneRecordResponse(Long id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
