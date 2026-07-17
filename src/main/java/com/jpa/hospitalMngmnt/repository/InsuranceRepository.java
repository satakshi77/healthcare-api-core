package com.jpa.hospitalMngmnt.repository;

import com.jpa.hospitalMngmnt.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}