package com.jpa.hospitalMngmnt.repository;

import com.jpa.hospitalMngmnt.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}