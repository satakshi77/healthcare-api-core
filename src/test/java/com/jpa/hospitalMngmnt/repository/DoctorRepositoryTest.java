package com.jpa.hospitalMngmnt.repository;

import com.jpa.hospitalMngmnt.entity.Doctor;
import com.jpa.hospitalMngmnt.entity.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void shouldSaveAndFindDoctor() {
        User user = new User();
        user.setUsername("dr_smith");
        user.setPassword("password");
        entityManager.persist(user);

        Doctor doctor = Doctor.builder()
                .name("Dr. Smith")
                .email("smith@hospital.com")
                .specialization("Cardiology")
                .user(user)
                .build();

        Doctor savedDoctor = doctorRepository.save(doctor);

        assertThat(savedDoctor.getId()).isNotNull();
        assertThat(savedDoctor.getName()).isEqualTo("Dr. Smith");
        assertThat(savedDoctor.getSpecialization()).isEqualTo("Cardiology");
    }
}