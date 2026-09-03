package com.jpa.hospitalMngmnt.service;

import com.jpa.hospitalMngmnt.dto.AppointmentResponseDto;
import com.jpa.hospitalMngmnt.dto.CreateAppointmentRequestDto;
import com.jpa.hospitalMngmnt.entity.Appointment;
import com.jpa.hospitalMngmnt.entity.Doctor;
import com.jpa.hospitalMngmnt.entity.Patient;
import com.jpa.hospitalMngmnt.repository.AppointmentRepository;
import com.jpa.hospitalMngmnt.repository.DoctorRepository;
import com.jpa.hospitalMngmnt.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AppointmentService appointmentService;

    @Test
    void createNewAppointmentShouldSucceed() {
        CreateAppointmentRequestDto request = new CreateAppointmentRequestDto();
        request.setDoctorId(1L);
        request.setPatientId(2L);
        request.setAppointmentTime(LocalDateTime.now());
        request.setReason("Consultation");

        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        Appointment appointment = new Appointment();
        AppointmentResponseDto responseDto = new AppointmentResponseDto();

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(2L)).thenReturn(Optional.of(patient));
        when(modelMapper.map(request, Appointment.class)).thenReturn(appointment);
        when(modelMapper.map(appointment, AppointmentResponseDto.class)).thenReturn(responseDto);

        AppointmentResponseDto result = appointmentService.createNewAppointment(request);

        assertThat(result).isNotNull();
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    void reassignAppointmentToAnotherDocShouldSucceed() {
        Appointment appointment = new Appointment();
        Doctor newDoctor = new Doctor();

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(doctorRepository.findById(2L)).thenReturn(Optional.of(newDoctor));

        Appointment updated = appointmentService.reassignAppointmentToAnotherDoc(1L, 2L);

        assertThat(updated.getDoctor()).isEqualTo(newDoctor);
    }

    @Test
    void getAllAppointmentsOfDoctorShouldReturnList() {
        Doctor doctor = new Doctor();
        Appointment appointment = new Appointment();
        doctor.setAppointment(Set.of(appointment));

        AppointmentResponseDto responseDto = new AppointmentResponseDto();

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(modelMapper.map(appointment, AppointmentResponseDto.class)).thenReturn(responseDto);

        var result = appointmentService.getAllAppointmentsOfDoctor(1L);

        assertThat(result).hasSize(1);
    }
}