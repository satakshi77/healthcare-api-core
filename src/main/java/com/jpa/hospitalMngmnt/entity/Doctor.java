package com.jpa.hospitalMngmnt.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    private User user;

    @Column(nullable=false,length=100)
    private String name;

    @Column(length=100)
    private String specialization;

    @Column(unique = true,length=100)
    private String email;

    @ManyToMany(mappedBy = "doctors")
    private Set<Department> departments= new HashSet<>();

    @OneToMany(mappedBy = "doctor")
    private Set<Appointment> appointment= new HashSet<>();
}
