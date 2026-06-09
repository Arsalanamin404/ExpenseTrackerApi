package org.arsalan.expensetrackerapi.expense.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.arsalan.expensetrackerapi.category.dto.CategoryExpenseDto;
import org.arsalan.expensetrackerapi.common.dto.ApiResponse;
import org.arsalan.expensetrackerapi.expense.dto.*;
import org.arsalan.expensetrackerapi.expense.service.IExpenseService;
import org.arsalan.expensetrackerapi.report.dto.MonthlyCategoryExpenseDto;
import org.arsalan.expensetrackerapi.report.dto.MonthlyExpenseSummaryDto;
import org.arsalan.expensetrackerapi.report.dto.MonthlyTotalDto;
import org.arsalan.expensetrackerapi.report.dto.YearlySummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    private final IExpenseService expenseService;

    public ExpenseController(IExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ExpenseResponseDto>> createExpense(@Valid @RequestBody CreateExpenseRequestDto dto, HttpServletRequest request) {
        ExpenseResponseDto expense = expenseService.createExpense(dto);
        ApiResponse<ExpenseResponseDto> response =
                ApiResponse.success(
                        HttpStatus.CREATED.value(),
                        "Expense created successfully",
                        expense,
                        request.getRequestURI()
                );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ExpenseResponseDto>>> getAllExpenses(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "true") boolean asc,
            HttpServletRequest request
    ) {
        Page<ExpenseResponseDto> expenses;

        if (category != null && !category.isBlank())
            expenses = expenseService.getExpenseByCategory(category, pageNumber, pageSize, asc);
        else if (startDate != null && endDate != null)
            expenses = expenseService.getExpensesBetweenDates(
                    startDate,
                    endDate,
                    pageNumber,
                    pageSize,
                    asc
            );
        else
            expenses = expenseService.getAllExpenses(pageNumber, pageSize, asc);

        ApiResponse<Page<ExpenseResponseDto>> response = ApiResponse
                .success(
                        HttpStatus.OK.value(),
                        "Expenses fetched successfully",
                        expenses,
                        request.getRequestURI()
                );

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseResponseDto>> getExpenseById(@PathVariable UUID id, HttpServletRequest request) {
        ExpenseResponseDto expense = expenseService.getExpenseById(id);

        ApiResponse<ExpenseResponseDto> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "Expense fetched successfully",
                expense,
                request.getRequestURI()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/monthly")
    public ResponseEntity<ApiResponse<Page<ExpenseResponseDto>>> getMonthlyExpenses(
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "true") boolean asc,
            HttpServletRequest request
    ) {
        Page<ExpenseResponseDto> expenses =
                expenseService.getMonthlyExpenses(
                        year,
                        month,
                        pageNumber,
                        pageSize,
                        asc
                );

        ApiResponse<Page<ExpenseResponseDto>> response =
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Monthly expenses fetched successfully",
                        expenses,
                        request.getRequestURI()
                );

        return ResponseEntity.ok(response);
    }



    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseResponseDto>> updateExpenseById(@PathVariable UUID id, @Valid @RequestBody CreateExpenseRequestDto dto, HttpServletRequest request) {
        ExpenseResponseDto expense = expenseService.updateExpenseById(id, dto);

        ApiResponse<ExpenseResponseDto> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "Expense updated successfully",
                expense,
                request.getRequestURI()
        );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/title")
    public ResponseEntity<ApiResponse<ExpenseResponseDto>> updateTitle(@PathVariable UUID id, @Valid @RequestBody UpdateTitleDto dto, HttpServletRequest request) {
        ExpenseResponseDto expense = expenseService.updateTitle(id, dto);
        ApiResponse<ExpenseResponseDto> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "Expense title updated successfully",
                expense,
                request.getRequestURI()
        );
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/category")
    public ResponseEntity<ApiResponse<ExpenseResponseDto>> updateCategory(@PathVariable UUID id, @Valid @RequestBody UpdateCategoryDto dto, HttpServletRequest request) {
        ExpenseResponseDto expense = expenseService.updateCategory(id, dto);
        ApiResponse<ExpenseResponseDto> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "Expense category updated successfully",
                expense,
                request.getRequestURI()
        );
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/amount")
    public ResponseEntity<ApiResponse<ExpenseResponseDto>> updateAmount(@PathVariable UUID id, @Valid @RequestBody UpdateAmountDto dto, HttpServletRequest request) {
        ExpenseResponseDto expense = expenseService.updateAmount(id, dto);
        ApiResponse<ExpenseResponseDto> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "Expense amount updated successfully",
                expense,
                request.getRequestURI()
        );
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/date")
    public ResponseEntity<ApiResponse<ExpenseResponseDto>> updateDate(@PathVariable UUID id, @Valid @RequestBody UpdateDateDto dto, HttpServletRequest request) {
        ExpenseResponseDto expense = expenseService.updateDate(id, dto);
        ApiResponse<ExpenseResponseDto> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "Expense date updated successfully",
                expense,
                request.getRequestURI()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteExpenseById(@PathVariable UUID id, HttpServletRequest request) {
        expenseService.deleteExpenseById(id);

        ApiResponse<Void> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "Expense with id " + id + " deleted successfully",
                null,
                request.getRequestURI()
        );
        return ResponseEntity.ok(response);
    }
}
