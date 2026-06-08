package org.arsalan.expensetrackerapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.arsalan.expensetrackerapi.dto.*;
import org.arsalan.expensetrackerapi.service.IExpenseService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/expenses")
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

    @GetMapping("/categories/total")
    public ResponseEntity<ApiResponse<Page<CategoryExpenseDto>>> getTotalExpenseByAllCategories(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            HttpServletRequest request
    ) {
        Page<CategoryExpenseDto> expense = expenseService.getTotalExpenseByAllCategories(pageNumber, pageSize);

        ApiResponse<Page<CategoryExpenseDto>> response = ApiResponse
                .success(
                        HttpStatus.OK.value(),
                        "Expenses fetched by categories successfully",
                        expense,
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

    @GetMapping("/monthly/total")
    public ResponseEntity<ApiResponse<MonthlyTotalDto>> getMonthlyTotal(
            @RequestParam int year,
            @RequestParam int month,
            HttpServletRequest request
    ) {

        MonthlyTotalDto total =
                expenseService.getMonthlyTotal(
                        year,
                        month
                );

        ApiResponse<MonthlyTotalDto> response =
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Monthly total fetched successfully",
                        total,
                        request.getRequestURI()
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/monthly/categories")
    public ResponseEntity<ApiResponse<Page<MonthlyCategoryExpenseDto>>>
    getMonthlyCategoryTotals(
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            HttpServletRequest request
    ) {

        Page<MonthlyCategoryExpenseDto> totals =
                expenseService.getMonthlyCategoryTotals(
                        year,
                        month,
                        pageNumber,
                        pageSize
                );

        ApiResponse<Page<MonthlyCategoryExpenseDto>> response =
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Monthly category totals fetched successfully",
                        totals,
                        request.getRequestURI()
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/reports/monthly-summary")
    public ResponseEntity<ApiResponse<List<MonthlyExpenseSummaryDto>>> getMonthlySummary(
            @RequestParam int year,
            HttpServletRequest request
    ) {

        List<MonthlyExpenseSummaryDto> summary =
                expenseService.getMonthlySummary(year);

        ApiResponse<List<MonthlyExpenseSummaryDto>> response =
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Monthly summary fetched successfully",
                        summary,
                        request.getRequestURI()
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/reports/category-summary")
    public ResponseEntity<ApiResponse<Page<CategoryExpenseDto>>> getCategorySummary(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            HttpServletRequest request
    ) {

        Page<CategoryExpenseDto> summary = expenseService.getCategorySummary(pageNumber,pageSize);

        ApiResponse<Page<CategoryExpenseDto>> response =
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Category summary fetched successfully",
                        summary,
                        request.getRequestURI()
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/reports/yearly-summary")
    public ResponseEntity<ApiResponse<YearlySummaryDto>> getYearlySummary(
            @RequestParam int year,
            HttpServletRequest request
    ) {

        YearlySummaryDto summary =
                expenseService.getYearlySummary(year);

        ApiResponse<YearlySummaryDto> response =
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Yearly summary fetched successfully",
                        summary,
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
