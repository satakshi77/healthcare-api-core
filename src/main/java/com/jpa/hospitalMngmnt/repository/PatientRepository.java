package com.jpa.hospitalMngmnt.repository;

import com.jpa.hospitalMngmnt.dto.BloodGrpCntEntity;
import com.jpa.hospitalMngmnt.entity.Patient;
import com.jpa.hospitalMngmnt.entity.type.BloodGrpType;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    Patient findByName(String name);
List<Patient>findByNameContaining(String query);

    @Query("SELECT p FROM Patient p where p.bloodgrp =?1")
        List<Patient> findByBloodgrp(@Param("bloodgrp") BloodGrpType bloodgrp);

    @Query("select p.bloodgrp,count(p) from Patient p group by p.bloodgrp")
    List<Object[]>countEachBloodGrpType();

    @Query(value = "select * from patient", nativeQuery = true)
    Page<Patient> findAllPatients(Pageable pageable);


    @Transactional
        @Modifying
        @Query("update Patient p set p.name = :name where p.id= :id")
    int updateNameWithId(@Param("name") String name, @Param("id") Long id);
    @Query("select p from  Patient p left join fetch p.appointments a left join fetch a.doctor ")
    List<Patient> findAllPatientWithAppointment();

}
