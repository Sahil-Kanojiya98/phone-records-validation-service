package com.example.phonerecords.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PhoneValidationResponse {

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("phone_validation")
    private PhoneValidation phoneValidation;

    public static class PhoneValidation {

        @JsonProperty("is_valid")
        private Boolean isValid;

        public PhoneValidation() {
        }

        public PhoneValidation(Boolean isValid) {
            this.isValid = isValid;
        }

        public Boolean getValid() {
            return isValid;
        }

        public void setValid(Boolean valid) {
            isValid = valid;
        }
    }

    public PhoneValidationResponse() {
    }

    public PhoneValidationResponse(String phoneNumber, PhoneValidation phoneValidation) {
        this.phoneNumber = phoneNumber;
        this.phoneValidation = phoneValidation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneValidation getPhoneValidation() {
        return phoneValidation;
    }

    public void setPhoneValidation(PhoneValidation phoneValidation) {
        this.phoneValidation = phoneValidation;
    }
}
