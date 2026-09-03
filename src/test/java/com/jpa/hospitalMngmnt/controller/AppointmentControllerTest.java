package com.jpa.hospitalMngmnt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpa.hospitalMngmnt.dto.AppointmentResponseDto;
import com.jpa.hospitalMngmnt.dto.CreateAppointmentRequestDto;
import com.jpa.hospitalMngmnt.service.AppointmentService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration,org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration,org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration",
        "spring.cache.type=none"
})
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AppointmentService appointmentService;

    @MockitoBean
    private org.springframework.data.redis.core.RedisTemplate<String, Object> redisTemplate;

    @MockitoBean
    private org.springframework.data.redis.core.StringRedisTemplate stringRedisTemplate;

    @MockitoBean
    private org.springframework.data.redis.connection.RedisConnectionFactory redisConnectionFactory;

    @Test
    @WithMockUser(username = "patient_user", roles = {"PATIENT"})
    void createAppointmentShouldReturnCreated() throws Exception {
        CreateAppointmentRequestDto request = new CreateAppointmentRequestDto();
        request.setDoctorId(1L);
        request.setPatientId(1L);
        request.setAppointmentTime(LocalDateTime.now().plusDays(1));
        request.setReason("Consultation");

        AppointmentResponseDto responseDto = new AppointmentResponseDto();
        responseDto.setId(1L);
        responseDto.setReason("Consultation");

        when(appointmentService.createNewAppointment(any(CreateAppointmentRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}