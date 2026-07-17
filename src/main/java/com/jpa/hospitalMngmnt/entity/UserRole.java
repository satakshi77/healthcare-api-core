package com.jpa.hospitalMngmnt.entity;

import com.jpa.hospitalMngmnt.entity.type.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_roles")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private RoleType role;
}
