package org.arsalan.expensetrackerapi.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MonthlyExpenseSummaryDto {

    private Integer month;
    private Double totalExpense;
}