package com.jpa.hospitalMngmnt.repository;

import com.jpa.hospitalMngmnt.entity.Patient;
import com.jpa.hospitalMngmnt.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PatientRepositoryTest {

    @Autowired
    private PatientRepository underTest;
    @Autowired
    private TestEntityManager entityManager;
    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }
    @Test
    void ShouldFindPatientByName() {
        User user = new User();
        user.setUsername("amy_user");
        user.setPassword("password");
        User savedUser=entityManager.persistAndFlush(user);
        String name = "Amy";
        Patient patient = Patient.builder()
                .name(name)
                .email("amy@gmail.com")
                .dob(LocalDate.of(1999, 12, 12))
                .gender("Female")
                .user(savedUser)
                .build();

        underTest.save(patient);

        Patient expected=underTest.findByName(name);

        assertThat(expected).isNotNull();
        assertThat(expected.getName()).isEqualTo(name);
        assertThat(expected.getUser().getId()).isEqualTo(savedUser.getId());
    }

  }