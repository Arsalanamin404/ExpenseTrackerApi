package org.arsalan.expensetrackerapi.service;

import org.arsalan.expensetrackerapi.dto.*;
import org.arsalan.expensetrackerapi.entity.Expense;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IExpenseService {
    ExpenseResponseDto createExpense(CreateExpenseRequestDto dto);

    Page<ExpenseResponseDto> getAllExpenses(int pageNumber, int pageSize, boolean asc);

    Page<ExpenseResponseDto> getExpenseByCategory(String category, int pageNumber, int pageSize, boolean asc);

    Page<CategoryExpenseDto> getTotalExpenseByAllCategories(int pageNumber, int pageSize);

    Page<ExpenseResponseDto> getExpensesBetweenDates(
            LocalDate startDate,
            LocalDate endDate,
            int pageNumber,
            int pageSize,
            boolean asc
    );

    Page<ExpenseResponseDto> getMonthlyExpenses(
            int year,
            int month,
            int pageNumber,
            int pageSize,
            boolean asc
    );

    MonthlyTotalDto getMonthlyTotal(int year, int month);

    Page<MonthlyCategoryExpenseDto> getMonthlyCategoryTotals(
            int year,
            int month,
            int pageNumber,
            int pageSize
    );

    List<MonthlyExpenseSummaryDto> getMonthlySummary(int year);

    Page<CategoryExpenseDto> getCategorySummary(
            int pageNumber,
            int pageSize
    );

    YearlySummaryDto getYearlySummary(int year);

    ExpenseResponseDto getExpenseById(UUID id);

    ExpenseResponseDto updateExpenseById(UUID id, CreateExpenseRequestDto dto);

    ExpenseResponseDto updateTitle(UUID id, UpdateTitleDto dto);

    ExpenseResponseDto updateAmount(UUID id, UpdateAmountDto dto);

    ExpenseResponseDto updateCategory(UUID id, UpdateCategoryDto dto);

    ExpenseResponseDto updateDate(UUID id, UpdateDateDto dto);

    void deleteExpenseById(UUID id);
}
