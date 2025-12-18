package com.example.phonerecords.service;

import com.example.phonerecords.dto.CreatePhoneRecordRequest;
import com.example.phonerecords.dto.PhoneRecordResponse;
import com.example.phonerecords.dto.PhoneValidationResponse;
import com.example.phonerecords.exception.PhoneAlreadyExistsException;
import com.example.phonerecords.exception.PhoneNotFoundException;
import com.example.phonerecords.exception.PhoneValidationException;
import com.example.phonerecords.model.PhoneRecord;
import com.example.phonerecords.repository.PhoneRecordRepository;
import com.example.phonerecords.validator.PhoneNumberValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneRecordServiceImpl implements PhoneRecordService {

    private final Logger log = LoggerFactory.getLogger(PhoneRecordServiceImpl.class);

    private final PhoneRecordRepository phoneRecordRepository;
    private final PhoneNumberValidator phoneNumberValidator;

    public PhoneRecordServiceImpl(PhoneRecordRepository phoneRecordRepository, PhoneNumberValidator phoneNumberValidator) {
        this.phoneRecordRepository = phoneRecordRepository;
        this.phoneNumberValidator = phoneNumberValidator;
    }

    @Transactional
    @Override
    public PhoneRecordResponse createPhoneRecord(CreatePhoneRecordRequest createPhoneRecordRequest) {
        log.info("Creating phone record for name: {}, phone: {}", createPhoneRecordRequest.getName(), createPhoneRecordRequest.getPhoneNumber());

        PhoneValidationResponse phoneValidationResponse = phoneNumberValidator.validatePhoneNumber(createPhoneRecordRequest.getPhoneNumber());
        if (phoneValidationResponse.getPhoneValidation() == null) {
            log.warn("Phone validation API returned empty response for {}", createPhoneRecordRequest.getPhoneNumber());
            throw new PhoneValidationException("Unable to validate phone number: " + createPhoneRecordRequest.getPhoneNumber());
        }

        boolean isValid = phoneValidationResponse.getPhoneValidation().getValid();
        if (!isValid) {
            log.warn("Phone number is invalid: {}", createPhoneRecordRequest.getPhoneNumber());
            throw new PhoneValidationException("Invalid phone number: " + createPhoneRecordRequest.getPhoneNumber());
        }

        String normalizedPhoneNumber = phoneValidationResponse.getPhoneNumber();

        if (phoneRecordRepository.existsByPhoneNumber(normalizedPhoneNumber)) {
            log.error("Phone number already exists: {}", createPhoneRecordRequest.getPhoneNumber());
            throw new PhoneAlreadyExistsException("Phone number already exists: " + createPhoneRecordRequest.getPhoneNumber());
        }

        PhoneRecord entity = new PhoneRecord();
        entity.setName(createPhoneRecordRequest.getName());
        entity.setPhoneNumber(normalizedPhoneNumber);

        PhoneRecord phoneRecord = phoneRecordRepository.save(entity);
        log.info("Phone record created with ID: {}", phoneRecord.getId());

        return new PhoneRecordResponse(
                phoneRecord.getId(),
                phoneRecord.getName(),
                phoneRecord.getPhoneNumber()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<PhoneRecordResponse> getAllPhoneRecords() {
        return phoneRecordRepository.findAllResponses();
    }

    @Transactional(readOnly = true)
    @Override
    public PhoneRecordResponse getPhoneRecordById(Long id) {
        log.info("Fetching phone record with ID: {}", id);
        Optional<PhoneRecordResponse> phoneRecordResponse = phoneRecordRepository.findResponseById(id);

        if (phoneRecordResponse.isEmpty()){
            log.error("Phone record not found with ID: {}", id);
            throw new PhoneNotFoundException("Phone record not found with id: " + id);
        }

        return phoneRecordResponse.get();
    }
}
