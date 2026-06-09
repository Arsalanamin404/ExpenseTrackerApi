package org.arsalan.expensetrackerapi.expense.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class UpdateAmountDto {
    @NotNull
    @Positive(message = "amount must be greater than 0")
    private Double amount;
}
