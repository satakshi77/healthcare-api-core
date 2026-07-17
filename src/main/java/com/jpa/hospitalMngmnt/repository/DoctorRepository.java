package com.jpa.hospitalMngmnt.repository;

import com.jpa.hospitalMngmnt.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}