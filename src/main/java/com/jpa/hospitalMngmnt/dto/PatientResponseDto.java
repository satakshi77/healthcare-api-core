package com.jpa.hospitalMngmnt.dto;

import com.jpa.hospitalMngmnt.entity.type.BloodGrpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponseDto {


        private Long id;
        private String name;
        private String gender;
        private LocalDate birthDate;
        private BloodGrpType bloodGroup;

}
