package org.arsalan.expensetrackerapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateCategoryDto {
    @NotBlank
    private UUID category_id;
}
