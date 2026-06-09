package org.arsalan.expensetrackerapi.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class YearlySummaryDto {

    private Integer year;
    private Double totalExpense;
}