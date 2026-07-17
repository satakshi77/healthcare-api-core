package com.jpa.hospitalMngmnt.entity;

import com.jpa.hospitalMngmnt.entity.type.BloodGrpType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "patient",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_patient_name", columnNames ={"email"}),
                @UniqueConstraint(name = "unique_patient_dob_gender", columnNames = {"dob","gender"})
        },
        indexes = {
                @Index(name = "idx_patient_birth_date", columnList = "dob")
        }
)
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable =false)
    private String name;

    @ToString.Exclude
    private LocalDate dob;
    private String gender;

    @Column(nullable = false,unique = true)
    private String email;

    @OneToOne
    @MapsId
    private  User user;
    @Enumerated(EnumType.STRING)
    private BloodGrpType bloodgrp;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST} ,orphanRemoval = true)
    private Insurance insurance;
    @OneToMany(mappedBy= "patient",fetch = FetchType.LAZY,cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

}
