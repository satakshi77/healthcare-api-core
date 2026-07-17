package com.jpa.hospitalMngmnt;

import com.jpa.hospitalMngmnt.entity.Patient;
import com.jpa.hospitalMngmnt.repository.PatientRepository;
import com.jpa.hospitalMngmnt.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootTest
public class PatientTest {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientService patientService;
    @Test
    public void testPatientRepository(){
    List<Patient> patientList= patientRepository.findAllPatientWithAppointment();
    System.out.println(patientList);
    }
    @Test
    public void testTransactionMethods(){

    Patient patient = patientRepository.findByName("Amita");
        Page<Patient> patientList= patientRepository.findAllPatients(PageRequest.of(0,2, Sort.by("name")));
    for(Patient p:patientList) {
        System.out.println(p);
    }

    int rowUpdated= patientRepository.updateNameWithId("Amit", 1L);
    System.out.println(rowUpdated);
    }


}
