package org.arsalan.expensetrackerapi.user.service;

import org.arsalan.expensetrackerapi.auth.entity.User;
import org.arsalan.expensetrackerapi.auth.repository.UserRepository;
import org.arsalan.expensetrackerapi.common.exception.ResourceNotFoundException;
import org.arsalan.expensetrackerapi.user.dto.UserSummaryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User findUserOrThrow(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id " + id
                        )
                );
    }

    private UserSummaryDto mapToDto(User user) {
        return new UserSummaryDto(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getExpenses().size()
        );
    }

    @Override
    public List<UserSummaryDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public UserSummaryDto getUserById(UUID userId) {
        return mapToDto(findUserOrThrow(userId));
    }

    @Override
    public void deleteUser(UUID userId) {
        User user = findUserOrThrow(userId);
        userRepository.delete(user);
    }
}