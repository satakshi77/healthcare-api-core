package com.jpa.hospitalMngmnt.dto;

import com.jpa.hospitalMngmnt.entity.type.BloodGrpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BloodGrpCntEntity {
    private BloodGrpType bloodGrpType;
    private Long cnt;

}
