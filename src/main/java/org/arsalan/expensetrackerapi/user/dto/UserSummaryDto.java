package org.arsalan.expensetrackerapi.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.arsalan.expensetrackerapi.common.enums.Role;
import org.arsalan.expensetrackerapi.expense.entity.Expense;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserSummaryDto {

        private UUID id;
        private String fullName;
        private String email;
        private Role role;
        private int totalExpenses;



}
