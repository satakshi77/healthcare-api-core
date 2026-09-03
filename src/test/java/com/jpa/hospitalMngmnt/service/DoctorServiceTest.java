package com.jpa.hospitalMngmnt.service;

import com.jpa.hospitalMngmnt.dto.DoctorResponseDto;
import com.jpa.hospitalMngmnt.dto.OnboardDoctorRequestDto;
import com.jpa.hospitalMngmnt.entity.Doctor;
import com.jpa.hospitalMngmnt.entity.User;
import com.jpa.hospitalMngmnt.repository.DoctorRepository;
import com.jpa.hospitalMngmnt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DoctorService doctorService;

    @Test
    void getAllDoctorsShouldReturnList() {
        Doctor doctor = Doctor.builder().name("Dr. House").specialization("Diagnostics").build();
        DoctorResponseDto dto = new DoctorResponseDto();
        dto.setName("Dr. House");

        when(doctorRepository.findAll()).thenReturn(List.of(doctor));
        when(modelMapper.map(doctor, DoctorResponseDto.class)).thenReturn(dto);

        List<DoctorResponseDto> result = doctorService.getAllDoctors();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Dr. House");
        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    void onBoardNewDoctorShouldSucceed() {
        OnboardDoctorRequestDto request = new OnboardDoctorRequestDto();
        request.setUserId(1L);
        request.setName("Dr. Wilson");
        request.setSpecialization("Oncology");

        User user = new User();
        user.setId(1L);
        user.setRoles(new HashSet<>());

        Doctor doctor = Doctor.builder().name("Dr. Wilson").specialization("Oncology").user(user).build();
        DoctorResponseDto responseDto = new DoctorResponseDto();
        responseDto.setName("Dr. Wilson");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(doctorRepository.existsById(1L)).thenReturn(false);
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);
        when(modelMapper.map(doctor, DoctorResponseDto.class)).thenReturn(responseDto);

        DoctorResponseDto result = doctorService.onBoardNewDoctor(request);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Dr. Wilson");
        verify(userRepository, times(1)).save(user);
        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }

    @Test
    void onBoardNewDoctorShouldThrowExceptionWhenAlreadyDoctor() {
        OnboardDoctorRequestDto request = new OnboardDoctorRequestDto();
        request.setUserId(1L);

        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(doctorRepository.existsById(1L)).thenReturn(true);

        assertThatThrownBy(() -> doctorService.onBoardNewDoctor(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Already a doctor");

        verify(doctorRepository, never()).save(any(Doctor.class));
    }
}