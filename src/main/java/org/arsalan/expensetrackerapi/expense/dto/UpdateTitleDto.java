package org.arsalan.expensetrackerapi.expense.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateTitleDto {
    @NotBlank
    private String title;
}
