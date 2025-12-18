package com.example.phonerecords.repository;

import com.example.phonerecords.dto.PhoneRecordResponse;
import com.example.phonerecords.model.PhoneRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhoneRecordRepository extends JpaRepository<PhoneRecord, Long> {

    @Query("SELECT new com.example.phonerecords.dto.PhoneRecordResponse(" +
            "p.id, p.name, p.phoneNumber) " +
            "FROM PhoneRecord p WHERE p.id = :id")
    Optional<PhoneRecordResponse> findResponseById(Long id);

    @Query("SELECT new com.example.phonerecords.dto.PhoneRecordResponse(" +
            "p.id, p.name, p.phoneNumber) " +
            "FROM PhoneRecord p")
    List<PhoneRecordResponse> findAllResponses();

    boolean existsByPhoneNumber(String phoneNumber);
}
