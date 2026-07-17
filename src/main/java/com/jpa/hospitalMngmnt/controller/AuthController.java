package com.jpa.hospitalMngmnt.controller;


import com.jpa.hospitalMngmnt.dto.LoginRequestDto;
import com.jpa.hospitalMngmnt.dto.LoginResponseDto;
import com.jpa.hospitalMngmnt.dto.SignupRequestDto;
import com.jpa.hospitalMngmnt.dto.SignupResponseDto;
import com.jpa.hospitalMngmnt.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto>login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));}
        @PostMapping("/signup")
        public ResponseEntity<SignupResponseDto>signup(@RequestBody SignupRequestDto signupRequestDto){
            return ResponseEntity.ok(authService.signup(signupRequestDto));

    }
}
