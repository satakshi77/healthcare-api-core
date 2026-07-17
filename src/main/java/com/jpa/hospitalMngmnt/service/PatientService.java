package com.jpa.hospitalMngmnt.service;

import com.jpa.hospitalMngmnt.dto.PatientResponseDto;
import com.jpa.hospitalMngmnt.entity.Patient;
import com.jpa.hospitalMngmnt.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    ModelMapper modelMapper = new ModelMapper();

    @Transactional
    public PatientResponseDto getPatientById(Long id){
       Patient patient =  patientRepository.findById(id).orElseThrow();

        return modelMapper.map(patient, PatientResponseDto.class);
    }
    public List<PatientResponseDto> getAllPatients(Integer pageNumber, Integer pageSize) {
        return patientRepository.findAllPatients(PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(patient -> modelMapper.map(patient, PatientResponseDto.class))
                .collect(Collectors.toList());
    }
}
