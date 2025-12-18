package com.example.phonerecords;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class PhoneRecordsApplication {

	private static final Logger log = LoggerFactory.getLogger(PhoneRecordsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PhoneRecordsApplication.class, args);
	}
}
