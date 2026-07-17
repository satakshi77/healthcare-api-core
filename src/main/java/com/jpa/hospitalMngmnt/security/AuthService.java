package com.jpa.hospitalMngmnt.security;

import com.jpa.hospitalMngmnt.dto.LoginRequestDto;
import com.jpa.hospitalMngmnt.dto.LoginResponseDto;
import com.jpa.hospitalMngmnt.dto.SignupRequestDto;
import com.jpa.hospitalMngmnt.dto.SignupResponseDto;
import com.jpa.hospitalMngmnt.entity.Patient;
import com.jpa.hospitalMngmnt.entity.User;
import com.jpa.hospitalMngmnt.entity.type.RoleType;
import com.jpa.hospitalMngmnt.repository.PatientRepository;
import com.jpa.hospitalMngmnt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jpa.hospitalMngmnt.entity.UserRole;
import java.util.stream.Collectors;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );
        User user = (User) authentication.getPrincipal();
        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDto(token, user.getId());
    }

    @Transactional
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {

        if (userRepository.findByUsername(signupRequestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User Already Exists");
        }

        User user = User.builder()
                .username(signupRequestDto.getUsername())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .build();


        Set<UserRole> userRoles = signupRequestDto.getRoles()
                .stream()
                .map(roleType -> UserRole.builder()
                        .role(roleType)
                        .user(user)
                        .build())
                .collect(Collectors.toSet());


        user.setRoles(userRoles);

        User savedUser = userRepository.save(user);


        Patient patient = Patient.builder()
                .name(signupRequestDto.getName())
                .email(signupRequestDto.getUsername())
                .user(savedUser)
                .build();

        patientRepository.save(patient);


        return new SignupResponseDto(
                savedUser.getId(),
                savedUser.getUsername()
        );
    }
}