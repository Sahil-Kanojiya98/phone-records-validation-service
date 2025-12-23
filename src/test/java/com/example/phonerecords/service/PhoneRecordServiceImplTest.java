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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PhoneRecordServiceImplTest {

    private PhoneRecordRepository repository;
    private PhoneNumberValidator validator;
    private PhoneRecordServiceImpl service;

    @BeforeEach
    void setup() {
        repository = mock(PhoneRecordRepository.class);
        validator = mock(PhoneNumberValidator.class);
        service = new PhoneRecordServiceImpl(repository, validator);
    }

    @Test
    void testCreatePhoneRecord_success() {
        CreatePhoneRecordRequest request = new CreatePhoneRecordRequest();
        request.setName("John Doe");
        request.setPhoneNumber("+1234567890");

        when(validator.validatePhoneNumber(request.getPhoneNumber())).thenReturn(true);
        when(repository.existsByPhoneNumber(request.getPhoneNumber())).thenReturn(false);

        PhoneRecord savedRecord = new PhoneRecord();
        savedRecord.setId(1L);
        savedRecord.setName("John Doe");
        savedRecord.setPhoneNumber("+1234567890");

        when(repository.save(any(PhoneRecord.class))).thenReturn(savedRecord);

        PhoneRecordResponse response = service.createPhoneRecord(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("John Doe", response.getName());
        assertEquals("+1234567890", response.getPhoneNumber());
        verify(repository, times(1)).save(any());
    }

    @Test
    void testCreatePhoneRecord_invalidPhone() {
        CreatePhoneRecordRequest request = new CreatePhoneRecordRequest();
        request.setName("Jane");
        request.setPhoneNumber("+111");

        when(validator.validatePhoneNumber("+111")).thenReturn(false);

        assertThrows(PhoneValidationException.class, () -> service.createPhoneRecord(request));
    }

    @Test
    void testCreatePhoneRecord_alreadyExists() {
        CreatePhoneRecordRequest request = new CreatePhoneRecordRequest();
        request.setName("John");
        request.setPhoneNumber("+1234567890");

        when(validator.validatePhoneNumber("+1234567890")).thenReturn(true);
        when(repository.existsByPhoneNumber("+1234567890")).thenReturn(true);

        assertThrows(PhoneAlreadyExistsException.class, () -> service.createPhoneRecord(request));
    }

    @Test
    void testGetPhoneRecordById_found() {
        PhoneRecordResponse response = new PhoneRecordResponse(1L, "John", "+1234567890");
        when(repository.findResponseById(1L)).thenReturn(Optional.of(response));

        PhoneRecordResponse result = service.getPhoneRecordById(1L);
        assertEquals("John", result.getName());
        assertEquals("+1234567890", result.getPhoneNumber());
    }

    @Test
    void testGetPhoneRecordById_notFound() {
        when(repository.findResponseById(1L)).thenReturn(Optional.empty());
        assertThrows(PhoneNotFoundException.class, () -> service.getPhoneRecordById(1L));
    }

    @Test
    void testGetAllPhoneRecords() {
        List<PhoneRecordResponse> responses = List.of(
                new PhoneRecordResponse(1L, "John", "+1234567890"),
                new PhoneRecordResponse(2L, "Jane", "+111222333")
        );
        when(repository.findAllResponses()).thenReturn(responses);

        List<PhoneRecordResponse> result = service.getAllPhoneRecords();
        assertEquals(2, result.size());
    }
}
