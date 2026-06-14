package org.arsalan.expensetrackerapi.auth.dto;

import lombok.Builder;
import lombok.Data;
import org.arsalan.expensetrackerapi.common.enums.Role;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UserResponseDto {

    private UUID id;

    private String fullName;

    private String email;

    private Role role;

    private LocalDateTime createdAt;
}