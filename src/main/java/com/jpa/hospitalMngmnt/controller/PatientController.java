package com.jpa.hospitalMngmnt.controller;


import com.jpa.hospitalMngmnt.dto.AppointmentResponseDto;
import com.jpa.hospitalMngmnt.dto.PatientResponseDto;
import com.jpa.hospitalMngmnt.dto.CreateAppointmentRequestDto;
import com.jpa.hospitalMngmnt.entity.User;
import com.jpa.hospitalMngmnt.service.AppointmentService;
import com.jpa.hospitalMngmnt.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponseDto> createNewAppointment(@RequestBody CreateAppointmentRequestDto createAppointmentRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createNewAppointment(createAppointmentRequestDto));

    }
    @GetMapping("/profile")
    public ResponseEntity<PatientResponseDto> getPatientProfile() {
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return ResponseEntity.ok(patientService.getPatientById(user.getId()));
    }
}
