package com.jpa.hospitalMngmnt.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true, length= 100)
    private String name;

    @OneToOne
    private Doctor headDoc;

    @ManyToMany
    @JoinTable
    private Set<Doctor> doctors= new HashSet<>();
}
