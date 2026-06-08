package org.arsalan.expensetrackerapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryExpenseDto {

    private String category;
    private Double totalExpense;
}