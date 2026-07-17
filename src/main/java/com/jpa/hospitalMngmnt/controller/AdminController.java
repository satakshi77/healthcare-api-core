package com.jpa.hospitalMngmnt.controller;

import com.jpa.hospitalMngmnt.dto.DoctorResponseDto;
import com.jpa.hospitalMngmnt.dto.OnboardDoctorRequestDto;
import com.jpa.hospitalMngmnt.dto.PatientResponseDto;
import com.jpa.hospitalMngmnt.service.DoctorService;
import com.jpa.hospitalMngmnt.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PatientService patientService;
    private final DoctorService doctorService;
    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize
    ) {

        return ResponseEntity.ok(patientService.getAllPatients(pageNumber, pageSize));
    }
    @PostMapping("/onBoardNewDoctor")
    public ResponseEntity<DoctorResponseDto>onBoardNewDoctor(@RequestBody OnboardDoctorRequestDto onboardDoctorRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.onBoardNewDoctor(onboardDoctorRequestDto));
    }
}