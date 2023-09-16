package com.hexhack.meetbackend.service;

import com.hexhack.meetbackend.dto.AuthenticationRequest;
import com.hexhack.meetbackend.dto.AuthenticationResponse;
import com.hexhack.meetbackend.dto.RegisterRequest;
import com.hexhack.meetbackend.model.Role;
import com.hexhack.meetbackend.model.Teacher;
import com.hexhack.meetbackend.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Teacher teacher = Teacher.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        teacherRepository.save(teacher);

        String jwtToken = jwtService.generateToken(teacher);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Teacher teacher = teacherRepository.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(teacher);


        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
