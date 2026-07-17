package com.jpa.hospitalMngmnt.entity;


import com.jpa.hospitalMngmnt.entity.type.RoleType;
import com.jpa.hospitalMngmnt.security.RolePermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="app_user", indexes = {
        @Index(name = "idx_provider_id_provider_type", columnList = "providerId,providerType" )
})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(unique = true)
    private String username;

    private String password;

    private String providerId;
    private String providerType;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRole> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        roles.forEach(userRole -> {

            RoleType role = userRole.getRole();

            Set<SimpleGrantedAuthority> permissions =
                    RolePermissionMapping.getAuthoritiesForRole(role);

            authorities.addAll(permissions);

            authorities.add(
                    new SimpleGrantedAuthority("ROLE_" + role.name())
            );
        });

        return authorities;
    }
}
