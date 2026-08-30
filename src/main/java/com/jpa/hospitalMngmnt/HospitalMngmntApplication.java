package com.jpa.hospitalMngmnt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
@SpringBootApplication
@EnableCaching
public class HospitalMngmntApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalMngmntApplication.class, args);
	}

}
