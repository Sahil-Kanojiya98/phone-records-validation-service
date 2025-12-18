package com.example.phonerecords.controller;

import com.example.phonerecords.dto.CreatePhoneRecordRequest;
import com.example.phonerecords.dto.PhoneRecordResponse;
import com.example.phonerecords.service.PhoneRecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PhoneRecordController.class)
@Import(PhoneRecordControllerTest.TestConfig.class)
class PhoneRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PhoneRecordService service;

    @Autowired
    private ObjectMapper objectMapper;

    private PhoneRecordResponse response;

    @BeforeEach
    void setup() {
        response = new PhoneRecordResponse(1L, "John Doe", "+1234567890");
    }

    @Test
    void testCreatePhoneRecord() throws Exception {
        CreatePhoneRecordRequest request = new CreatePhoneRecordRequest();
        request.setName("John Doe");
        request.setPhoneNumber("+1234567890");

        when(service.createPhoneRecord(any(CreatePhoneRecordRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/phones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.phoneNumber").value("+1234567890"));
    }

    @Test
    void testGetAllPhoneRecords() throws Exception {
        when(service.getAllPhoneRecords()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/phones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].phoneNumber").value("+1234567890"));
    }

    @Test
    void testGetPhoneRecordById() throws Exception {
        when(service.getPhoneRecordById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/phones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.phoneNumber").value("+1234567890"));
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        PhoneRecordService phoneRecordService() {
            return Mockito.mock(PhoneRecordService.class);
        }
    }
}
