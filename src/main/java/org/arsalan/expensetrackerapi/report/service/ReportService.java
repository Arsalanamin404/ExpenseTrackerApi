package org.arsalan.expensetrackerapi.report.service;

import org.arsalan.expensetrackerapi.category.dto.CategoryExpenseDto;
import org.arsalan.expensetrackerapi.expense.repository.ExpenseRepository;
import org.arsalan.expensetrackerapi.report.dto.MonthlyCategoryExpenseDto;
import org.arsalan.expensetrackerapi.report.dto.MonthlyExpenseSummaryDto;
import org.arsalan.expensetrackerapi.report.dto.MonthlyTotalDto;
import org.arsalan.expensetrackerapi.report.dto.YearlySummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService implements IReportService {
    private final ExpenseRepository expenseRepository;

    public ReportService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Page<CategoryExpenseDto> getTotalExpenseByAllCategories(
            int pageNumber,
            int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return expenseRepository.getTotalExpenseByAllCategories(pageable);
    }

    @Override
    public MonthlyTotalDto getMonthlyTotal(int year, int month) {
        Double total = expenseRepository.getMonthlyTotal(year, month);
        return new MonthlyTotalDto(
                year,
                month,
                total
        );
    }

    @Override
    public Page<MonthlyCategoryExpenseDto> getMonthlyCategoryTotals(
            int year,
            int month,
            int pageNumber,
            int pageSize
    ) {

        LocalDate startDate = LocalDate.of(year, month, 1);

        LocalDate endDate = startDate.withDayOfMonth(
                startDate.lengthOfMonth()
        );

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return expenseRepository.getMonthlyCategoryTotals(
                startDate,
                endDate,
                pageable
        );
    }

    @Override
    public List<MonthlyExpenseSummaryDto> getMonthlySummary(int year) {
        return expenseRepository.getMonthlySummary(year);
    }


    @Override
    public YearlySummaryDto getYearlySummary(int year) {
        Double totalExpense = expenseRepository.getYearlyTotal(year);
        return new YearlySummaryDto(year, totalExpense);
    }
}
