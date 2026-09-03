package com.jpa.hospitalMngmnt.controller;

import com.jpa.hospitalMngmnt.dto.AppointmentResponseDto;
import com.jpa.hospitalMngmnt.entity.User;
import com.jpa.hospitalMngmnt.service.AppointmentService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

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
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @MockitoBean
    private AppointmentService appointmentService;

    @MockitoBean
    private org.springframework.data.redis.core.RedisTemplate<String, Object> redisTemplate;

    @MockitoBean
    private org.springframework.data.redis.core.StringRedisTemplate stringRedisTemplate;

    @MockitoBean
    private org.springframework.data.redis.connection.RedisConnectionFactory redisConnectionFactory;

    @Test
    void getAllAppointmentsOfDoctorShouldReturnList() throws Exception {
        User savedUser = new User();
        savedUser.setUsername("dr_jones");
        savedUser.setPassword("password");
        entityManager.persist(savedUser);

        AppointmentResponseDto dto = new AppointmentResponseDto();
        dto.setId(1L);
        dto.setReason("Routine Checkup");
        dto.setAppointmentTime(LocalDateTime.now());

        when(appointmentService.getAllAppointmentsOfDoctor(savedUser.getId())).thenReturn(List.of(dto));

        mockMvc.perform(get("/doctors/appointments")
                        .with(user(savedUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].reason").value("Routine Checkup"));
    }
}