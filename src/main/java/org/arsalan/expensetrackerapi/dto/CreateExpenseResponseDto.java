package org.arsalan.expensetrackerapi.dto;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateExpenseResponseDto {
    private String title;
    private Double amount;
    private String category;
    private LocalDate date;
}
