package com.jpa.hospitalMngmnt.service;

import com.jpa.hospitalMngmnt.entity.Insurance;
import com.jpa.hospitalMngmnt.entity.Patient;
import com.jpa.hospitalMngmnt.repository.InsuranceRepository;
import com.jpa.hospitalMngmnt.repository.PatientRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class InsuranceService {
    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Patient assignInsuranceToPatient(Insurance insurance, Long patientId){
        Patient patient= patientRepository.findById(patientId).orElseThrow(() -> new EntityExistsException("Patient not found with id" + patientId));
        patient.setInsurance(insurance);
        insurance.setPatient(patient);
        return patient;
    }
    @Transactional
    public Patient disassociateInsuranceFromPatient(Long patientId){
        Patient patient= patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id" + patientId));
        patient.setInsurance(null);
        return patient;
    }
}
