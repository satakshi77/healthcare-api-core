package com.jpa.hospitalMngmnt.controller;

import com.jpa.hospitalMngmnt.dto.AppointmentResponseDto;
import com.jpa.hospitalMngmnt.dto.CreateAppointmentRequestDto;
import com.jpa.hospitalMngmnt.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponseDto> createAppointment(@RequestBody @Valid CreateAppointmentRequestDto request) {
        AppointmentResponseDto created = appointmentService.createNewAppointment(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}