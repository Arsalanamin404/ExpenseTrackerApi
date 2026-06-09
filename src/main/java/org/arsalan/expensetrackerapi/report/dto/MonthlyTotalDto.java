package org.arsalan.expensetrackerapi.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MonthlyTotalDto {

    private int year;
    private int month;
    private Double totalExpense;
}