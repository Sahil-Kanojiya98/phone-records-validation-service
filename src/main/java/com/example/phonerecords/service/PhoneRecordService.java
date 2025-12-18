package com.example.phonerecords.service;

import com.example.phonerecords.dto.CreatePhoneRecordRequest;
import com.example.phonerecords.dto.PhoneRecordResponse;

import java.util.List;

public interface PhoneRecordService {

    PhoneRecordResponse createPhoneRecord(CreatePhoneRecordRequest createPhoneRecordRequest);

    List<PhoneRecordResponse> getAllPhoneRecords();

    PhoneRecordResponse getPhoneRecordById(Long id);
}
