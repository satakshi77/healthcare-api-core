package com.jpa.hospitalMngmnt.controller;

import com.jpa.hospitalMngmnt.entity.Patient;
import com.jpa.hospitalMngmnt.entity.User;
import com.jpa.hospitalMngmnt.repository.PatientRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration,org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration,org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration",
        "spring.cache.type=none"
})
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private EntityManager entityManager;

    @MockitoBean
    private org.springframework.data.redis.core.RedisTemplate<String, Object> redisTemplate;

    @MockitoBean
    private org.springframework.data.redis.core.StringRedisTemplate stringRedisTemplate;

    @MockitoBean
    private RedisConnectionFactory redisConnectionFactory;

    @BeforeEach
    void setUp() {
        patientRepository.deleteAll();

        RedisConnection redisConnection = Mockito.mock(RedisConnection.class);
        StringRedisConnection stringRedisConnection = Mockito.mock(StringRedisConnection.class);

        when(redisConnectionFactory.getConnection()).thenReturn(redisConnection);
        when(redisConnection.stringCommands()).thenReturn(stringRedisConnection);
    }

    @Test
    @WithMockUser(username = "john_user", roles = {"PATIENT"})
    void getPatientProfile() throws Exception {
        User savedUser = new User();
        savedUser.setUsername("john_user");
        savedUser.setPassword("password");
        entityManager.persist(savedUser);

        Patient patient = Patient.builder()
                .name("John")
                .email("John19@gmail.com")
                .dob(LocalDate.of(1990, 7, 1))
                .gender("M")
                .user(savedUser)
                .build();

        patientRepository.save(patient);

        mockMvc.perform(get("/patients/profile")
                        .with(user(savedUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.gender").value("M"));
    }
}