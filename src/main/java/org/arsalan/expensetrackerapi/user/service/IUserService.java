package org.arsalan.expensetrackerapi.user.service;

import org.arsalan.expensetrackerapi.user.dto.UserSummaryDto;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    List<UserSummaryDto> getAllUsers();

    UserSummaryDto getUserById(UUID userId);

    void deleteUser(UUID userId);
}