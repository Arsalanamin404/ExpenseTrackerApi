package org.arsalan.expensetrackerapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateDateDto {
    @NotBlank
    private LocalDate date;
}
