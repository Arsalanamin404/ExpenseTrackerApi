package org.arsalan.expensetrackerapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class ExpenseResponseDto {
    private UUID id;
    private String title;
    private Double amount;
    private UUID categoryId;
    private String category;
    private LocalDate date;
}