package org.arsalan.expensetrackerapi.report.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.arsalan.expensetrackerapi.category.dto.CategoryExpenseDto;
import org.arsalan.expensetrackerapi.common.dto.ApiResponse;
import org.arsalan.expensetrackerapi.report.dto.MonthlyCategoryExpenseDto;
import org.arsalan.expensetrackerapi.report.dto.MonthlyExpenseSummaryDto;
import org.arsalan.expensetrackerapi.report.dto.MonthlyTotalDto;
import org.arsalan.expensetrackerapi.report.dto.YearlySummaryDto;
import org.arsalan.expensetrackerapi.report.service.IReportService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final IReportService reportService;

    public ReportController(IReportService reportService){
        this.reportService = reportService;
    }

    @GetMapping("/categories/total")
    public ResponseEntity<ApiResponse<Page<CategoryExpenseDto>>> getTotalExpenseByAllCategories(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            HttpServletRequest request
    ) {
        Page<CategoryExpenseDto> expense = reportService.getTotalExpenseByAllCategories(pageNumber, pageSize);

        ApiResponse<Page<CategoryExpenseDto>> response = ApiResponse
                .success(
                        HttpStatus.OK.value(),
                        "Expenses fetched by categories successfully",
                        expense,
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
                reportService.getMonthlyTotal(
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
                reportService.getMonthlyCategoryTotals(
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

    @GetMapping("/monthly-summary")
    public ResponseEntity<ApiResponse<List<MonthlyExpenseSummaryDto>>> getMonthlySummary(
            @RequestParam int year,
            HttpServletRequest request
    ) {

        List<MonthlyExpenseSummaryDto> summary =
                reportService.getMonthlySummary(year);

        ApiResponse<List<MonthlyExpenseSummaryDto>> response =
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Monthly summary fetched successfully",
                        summary,
                        request.getRequestURI()
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/yearly-summary")
    public ResponseEntity<ApiResponse<YearlySummaryDto>> getYearlySummary(
            @RequestParam int year,
            HttpServletRequest request
    ) {

        YearlySummaryDto summary =
                reportService.getYearlySummary(year);

        ApiResponse<YearlySummaryDto> response =
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Yearly summary fetched successfully",
                        summary,
                        request.getRequestURI()
                );

        return ResponseEntity.ok(response);
    }



}
