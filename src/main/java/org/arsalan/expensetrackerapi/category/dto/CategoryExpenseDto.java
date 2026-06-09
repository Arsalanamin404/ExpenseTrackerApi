package org.arsalan.expensetrackerapi.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryExpenseDto {

    private String category;
    private Double totalExpense;
}