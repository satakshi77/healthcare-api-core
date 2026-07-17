package com.jpa.hospitalMngmnt.repository;

import com.jpa.hospitalMngmnt.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}