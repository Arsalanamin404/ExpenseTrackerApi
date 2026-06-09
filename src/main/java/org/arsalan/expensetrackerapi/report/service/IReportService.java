package org.arsalan.expensetrackerapi.report.service;

import org.arsalan.expensetrackerapi.category.dto.CategoryExpenseDto;
import org.arsalan.expensetrackerapi.report.dto.MonthlyCategoryExpenseDto;
import org.arsalan.expensetrackerapi.report.dto.MonthlyExpenseSummaryDto;
import org.arsalan.expensetrackerapi.report.dto.MonthlyTotalDto;
import org.arsalan.expensetrackerapi.report.dto.YearlySummaryDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IReportService {

    Page<CategoryExpenseDto> getTotalExpenseByAllCategories(
            int pageNumber,
            int pageSize
    );

    MonthlyTotalDto getMonthlyTotal(
            int year,
            int month
    );

    Page<MonthlyCategoryExpenseDto> getMonthlyCategoryTotals(
            int year,
            int month,
            int pageNumber,
            int pageSize
    );

    List<MonthlyExpenseSummaryDto> getMonthlySummary(
            int year
    );

    YearlySummaryDto getYearlySummary(
            int year
    );
}
