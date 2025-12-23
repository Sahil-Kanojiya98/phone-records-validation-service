package com.example.phonerecords.validator;

import com.example.phonerecords.config.AbstractApiProperties;
import com.example.phonerecords.dto.PhoneValidationResponse;
import com.example.phonerecords.exception.PhoneValidationServiceUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class PhoneNumberValidator {

    private final Logger log = LoggerFactory.getLogger(PhoneNumberValidator.class);

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String baseUrl;

    public PhoneNumberValidator(AbstractApiProperties properties) {
        this.restTemplate = new RestTemplate();
        this.apiKey = properties.getKey();
        this.baseUrl = properties.getUrl();
    }

    public boolean validatePhoneNumber(String phoneNumber) {
        final String url = baseUrl + "?api_key=" + apiKey + "&phone=" + phoneNumber;

        try {
            ResponseEntity<PhoneValidationResponse> response =
                    restTemplate.getForEntity(url, PhoneValidationResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                PhoneValidationResponse phoneValidationResponse = response.getBody();
                return phoneValidationResponse.getPhoneValidation().getValid();
            } else {
                log.error("Phone validation API returned non-OK status: {}", response.getStatusCode());
                throw new PhoneValidationServiceUnavailableException("Phone validation service unavailable. Status: " + response.getStatusCode());
            }
        } catch (HttpServerErrorException e) {
            log.error("Phone validation service returned server error for number {}: {}", phoneNumber, e.getMessage());
            throw new PhoneValidationServiceUnavailableException("Phone validation service unavailable", e);
        } catch (RestClientException e) {
            log.error("Phone validation API call failed for number {}: {}", phoneNumber, e.getMessage());
            throw new PhoneValidationServiceUnavailableException("Phone validation service unavailable", e);
        } catch (Exception e) {
            log.error("Unexpected error during phone validation for number {}: {}", phoneNumber, e.getMessage(), e);
            throw new PhoneValidationServiceUnavailableException("Phone validation service unavailable", e);
        }
    }
}
