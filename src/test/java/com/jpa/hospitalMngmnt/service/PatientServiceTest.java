package com.jpa.hospitalMngmnt.service;

import com.jpa.hospitalMngmnt.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
    @Mock
    private PatientRepository patientRepository;
    private PatientService underTest;

    @BeforeEach
    void setUp() {
        underTest = new PatientService(patientRepository);
    }

    @Test
    void canGetAllPatientsWithPagination() {
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        given(patientRepository.findAllPatients(any(Pageable.class))).willReturn(Page.empty());
        underTest.getAllPatients(pageNumber, pageSize);
        verify(patientRepository).findAllPatients(pageable);
    }
}