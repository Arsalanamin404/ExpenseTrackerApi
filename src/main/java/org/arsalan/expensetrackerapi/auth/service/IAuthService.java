package org.arsalan.expensetrackerapi.auth.service;

import org.arsalan.expensetrackerapi.auth.dto.AuthResponseDto;
import org.arsalan.expensetrackerapi.auth.dto.LoginRequestDto;
import org.arsalan.expensetrackerapi.auth.dto.RegisterRequestDto;
import org.arsalan.expensetrackerapi.auth.dto.UserResponseDto;

public interface IAuthService {
    UserResponseDto register(RegisterRequestDto request);

    AuthResponseDto login(LoginRequestDto request);
}