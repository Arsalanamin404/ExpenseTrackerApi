package org.arsalan.expensetrackerapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MonthlyTotalDto {

    private int year;
    private int month;
    private Double totalExpense;
}