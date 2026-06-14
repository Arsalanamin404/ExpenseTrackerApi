package org.arsalan.expensetrackerapi.auth.controller;

import jakarta.validation.Valid;
import org.arsalan.expensetrackerapi.auth.dto.AuthResponseDto;
import org.arsalan.expensetrackerapi.auth.dto.LoginRequestDto;
import org.arsalan.expensetrackerapi.auth.dto.RegisterRequestDto;
import org.arsalan.expensetrackerapi.auth.dto.UserResponseDto;
import org.arsalan.expensetrackerapi.auth.service.AuthServiceImpl;
import org.arsalan.expensetrackerapi.auth.service.IAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController{

    private final IAuthService authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(
            @Valid @RequestBody RegisterRequestDto request) {

        UserResponseDto response = authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @Valid @RequestBody LoginRequestDto request) {

        AuthResponseDto response = authService.login(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}