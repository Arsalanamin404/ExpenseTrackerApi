package org.arsalan.expensetrackerapi.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.arsalan.expensetrackerapi.auth.dto.AuthResponseDto;
import org.arsalan.expensetrackerapi.auth.dto.LoginRequestDto;
import org.arsalan.expensetrackerapi.auth.dto.RegisterRequestDto;
import org.arsalan.expensetrackerapi.auth.dto.UserResponseDto;
import org.arsalan.expensetrackerapi.auth.service.IAuthService;
import org.arsalan.expensetrackerapi.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> register(
            @Valid @RequestBody RegisterRequestDto requestDto,
            HttpServletRequest request) {

        UserResponseDto user = authService.register(requestDto);

        ApiResponse<UserResponseDto> response =
                ApiResponse.success(
                        HttpStatus.CREATED.value(),
                        "User registered successfully",
                        user,
                        request.getRequestURI()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDto>> login(
            @Valid @RequestBody LoginRequestDto requestDto,
            HttpServletRequest request) {

        AuthResponseDto auth = authService.login(requestDto);

        ApiResponse<AuthResponseDto> response =
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Login successful",
                        auth,
                        request.getRequestURI()
                );

        return ResponseEntity.ok(response);
    }
}