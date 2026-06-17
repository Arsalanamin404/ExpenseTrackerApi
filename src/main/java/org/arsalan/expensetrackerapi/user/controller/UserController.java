package org.arsalan.expensetrackerapi.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.arsalan.expensetrackerapi.common.dto.ApiResponse;
import org.arsalan.expensetrackerapi.user.dto.UserSummaryDto;
import org.arsalan.expensetrackerapi.user.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserSummaryDto>>> getAllUsers(
            HttpServletRequest request
    ) {

        ApiResponse<List<UserSummaryDto>> response =
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Users fetched successfully",
                        userService.getAllUsers(),
                        request.getRequestURI()
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserSummaryDto>> getUserById(
            @PathVariable UUID id,
            HttpServletRequest request
    ) {

        ApiResponse<UserSummaryDto> response =
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "User fetched successfully",
                        userService.getUserById(id),
                        request.getRequestURI()
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable UUID id,
            HttpServletRequest request
    ) {

        userService.deleteUser(id);

        ApiResponse<Void> response =
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "User deleted successfully",
                        null,
                        request.getRequestURI()
                );

        return ResponseEntity.ok(response);
    }
}