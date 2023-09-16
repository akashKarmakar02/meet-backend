package com.hexhack.meetbackend.controller;


import com.hexhack.meetbackend.dto.AuthenticationRequest;
import com.hexhack.meetbackend.dto.AuthenticationResponse;
import com.hexhack.meetbackend.dto.RegisterRequest;
import com.hexhack.meetbackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        System.out.println(request.getEmail());
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/chech")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello from Unsafe");
    }

}
