package com.jpa.hospitalMngmnt.service;

import com.jpa.hospitalMngmnt.dto.AppointmentResponseDto;
import com.jpa.hospitalMngmnt.entity.Appointment;
import com.jpa.hospitalMngmnt.entity.Doctor;
import com.jpa.hospitalMngmnt.entity.Patient;
import com.jpa.hospitalMngmnt.repository.AppointmentRepository;
import com.jpa.hospitalMngmnt.repository.DoctorRepository;
import com.jpa.hospitalMngmnt.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.jpa.hospitalMngmnt.dto.CreateAppointmentRequestDto;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    @Transactional
    @Secured("ROLE_PATIENT")
    public AppointmentResponseDto createNewAppointment(CreateAppointmentRequestDto request) {

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow();

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow();

        Appointment appointment = modelMapper.map(request, Appointment.class);

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        appointmentRepository.save(appointment);

        return modelMapper.map(appointment, AppointmentResponseDto.class);
    }
    @Transactional
    @PreAuthorize("hasAuthority('appointment:write')  or (#doctorId==authentication.principal.id)")
    public Appointment reassignAppointmentToAnotherDoc(Long appointmentId,Long doctorId) {
        Appointment appointment=appointmentRepository.findById(appointmentId).orElseThrow();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        appointment.setDoctor(doctor);
        return appointment;
    }
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('DOCTOR') and #doctorId==authentication.principal.id)")
    public List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        return doctor.getAppointment()
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }

}
