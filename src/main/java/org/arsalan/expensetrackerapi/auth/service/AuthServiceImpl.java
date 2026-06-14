package org.arsalan.expensetrackerapi.auth.service;


import org.arsalan.expensetrackerapi.auth.dto.AuthResponseDto;
import org.arsalan.expensetrackerapi.auth.dto.LoginRequestDto;
import org.arsalan.expensetrackerapi.auth.dto.RegisterRequestDto;
import org.arsalan.expensetrackerapi.auth.dto.UserResponseDto;
import org.arsalan.expensetrackerapi.auth.entity.User;
import org.arsalan.expensetrackerapi.auth.repository.UserRepository;
import org.arsalan.expensetrackerapi.common.exception.ResourceAlreadyExistsException;
import org.arsalan.expensetrackerapi.security.jwt.JwtService;
import org.arsalan.expensetrackerapi.security.service.CustomUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class AuthServiceImpl implements IAuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }


    @Override
    public UserResponseDto register(RegisterRequestDto request) {

        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new ResourceAlreadyExistsException("User with this email already exists");
                });

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        return UserResponseDto.builder()
                .id(savedUser.getId())
                .fullName(savedUser.getFullName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .createdAt(savedUser.getCreatedAt())
                .build();
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);

        return AuthResponseDto.builder()
                .token(token)
                .build();
    }
}
