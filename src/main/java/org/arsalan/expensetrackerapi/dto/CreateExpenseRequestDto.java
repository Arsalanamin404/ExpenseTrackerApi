package org.arsalan.expensetrackerapi.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;


@Getter
public class CreateExpenseRequestDto {
    @NotBlank(message = "title is required")
    private String title;

    @NotNull
    @Positive
    @DecimalMin(value = "1.0", message = "amount must be greater than 0")
    private Double amount;

    @NotNull(message = "category is required")
    private UUID category_id;

    @NotNull
    private LocalDate date;
}
