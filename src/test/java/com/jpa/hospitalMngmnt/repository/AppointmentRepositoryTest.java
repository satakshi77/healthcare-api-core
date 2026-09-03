package com.jpa.hospitalMngmnt.repository;

import com.jpa.hospitalMngmnt.entity.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void shouldSaveAndFindAppointment() {
        User user1 = new User();
        user1.setUsername("doc_user");
        user1.setPassword("pass");
        entityManager.persist(user1);

        Doctor doctor = Doctor.builder().name("Dr. Test").user(user1).build();
        entityManager.persist(doctor);

        User user2 = new User();
        user2.setUsername("patient_user");
        user2.setPassword("pass");
        entityManager.persist(user2);

        Patient patient = Patient.builder().name("Patient Test").email("p@test.com").user(user2).build();
        entityManager.persist(patient);

        Appointment appointment = Appointment.builder()
                .doctor(doctor)
                .patient(patient)
                .appointmntTime(LocalDateTime.now())
                .reason("Checkup")
                .build();

        Appointment saved = appointmentRepository.save(appointment);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getReason()).isEqualTo("Checkup");
    }
}