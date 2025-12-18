package com.example.phonerecords.controller;

import com.example.phonerecords.dto.CreatePhoneRecordRequest;
import com.example.phonerecords.dto.PhoneRecordResponse;
import com.example.phonerecords.service.PhoneRecordService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phones")
public class PhoneRecordController {

    private static final Logger log = LoggerFactory.getLogger(PhoneRecordController.class);

    private final PhoneRecordService phoneRecordService;

    public PhoneRecordController(PhoneRecordService phoneRecordService) {
        this.phoneRecordService = phoneRecordService;
    }

    @PostMapping
    public ResponseEntity<PhoneRecordResponse> create(@Valid @RequestBody CreatePhoneRecordRequest createPhoneRecordRequest) {
        log.info("Received request to create phone record: {}", createPhoneRecordRequest);
        PhoneRecordResponse phoneRecordResponse = phoneRecordService.createPhoneRecord(createPhoneRecordRequest);
        return new ResponseEntity<>(phoneRecordResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PhoneRecordResponse>> getAll() {
        log.info("Received request to fetch all phone records");
        List<PhoneRecordResponse> phoneRecordResponseList = phoneRecordService.getAllPhoneRecords();
        return new ResponseEntity<>(phoneRecordResponseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhoneRecordResponse> getById(@PathVariable Long id) {
        log.info("Received request to fetch phone record with id: {}", id);
        PhoneRecordResponse phoneRecordResponse = phoneRecordService.getPhoneRecordById(id);
        return new ResponseEntity<>(phoneRecordResponse, HttpStatus.OK);
    }
}
