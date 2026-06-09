package org.arsalan.expensetrackerapi.expense.service;

import org.arsalan.expensetrackerapi.category.dto.CategoryExpenseDto;
import org.arsalan.expensetrackerapi.expense.dto.*;
import org.arsalan.expensetrackerapi.report.dto.MonthlyCategoryExpenseDto;
import org.arsalan.expensetrackerapi.report.dto.MonthlyExpenseSummaryDto;
import org.arsalan.expensetrackerapi.report.dto.MonthlyTotalDto;
import org.arsalan.expensetrackerapi.report.dto.YearlySummaryDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IExpenseService {
    ExpenseResponseDto createExpense(CreateExpenseRequestDto dto);

    Page<ExpenseResponseDto> getAllExpenses(int pageNumber, int pageSize, boolean asc);

    ExpenseResponseDto getExpenseById(UUID id);

    ExpenseResponseDto updateExpenseById(UUID id, CreateExpenseRequestDto dto);

    ExpenseResponseDto updateTitle(UUID id, UpdateTitleDto dto);

    ExpenseResponseDto updateAmount(UUID id, UpdateAmountDto dto);

    ExpenseResponseDto updateCategory(UUID id, UpdateCategoryDto dto);

    ExpenseResponseDto updateDate(UUID id, UpdateDateDto dto);

    void deleteExpenseById(UUID id);

    Page<ExpenseResponseDto> getExpenseByCategory(String category, int pageNumber, int pageSize, boolean asc);

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
}

