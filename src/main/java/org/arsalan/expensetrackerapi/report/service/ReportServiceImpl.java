package org.arsalan.expensetrackerapi.report.service;

import org.arsalan.expensetrackerapi.auth.entity.User;
import org.arsalan.expensetrackerapi.auth.repository.UserRepository;
import org.arsalan.expensetrackerapi.category.dto.CategoryExpenseDto;
import org.arsalan.expensetrackerapi.common.exception.ResourceNotFoundException;
import org.arsalan.expensetrackerapi.expense.repository.ExpenseRepository;
import org.arsalan.expensetrackerapi.report.dto.MonthlyCategoryExpenseDto;
import org.arsalan.expensetrackerapi.report.dto.MonthlyExpenseSummaryDto;
import org.arsalan.expensetrackerapi.report.dto.MonthlyTotalDto;
import org.arsalan.expensetrackerapi.report.dto.YearlySummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportServiceImpl implements IReportService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();

        if (authentication == null) {
            throw new AuthenticationCredentialsNotFoundException("No authenticated user found");
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email " + email
                        ));

    }


    public ReportServiceImpl(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<CategoryExpenseDto> getTotalExpenseByAllCategories(
            int pageNumber,
            int pageSize) {

        User currentUser = getCurrentUser();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return expenseRepository.getTotalExpenseByAllCategories(currentUser, pageable);
    }

    @Override
    public MonthlyTotalDto getMonthlyTotal(int year, int month) {
        User currentUser = getCurrentUser();

        Double total = expenseRepository.getMonthlyTotal(currentUser, year, month);
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
        User currentUser = getCurrentUser();

        LocalDate startDate = LocalDate.of(year, month, 1);

        LocalDate endDate = startDate.withDayOfMonth(
                startDate.lengthOfMonth()
        );

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return expenseRepository.getMonthlyCategoryTotals(
                currentUser,
                startDate,
                endDate,
                pageable
        );
    }

    @Override
    public List<MonthlyExpenseSummaryDto> getMonthlySummary(int year) {
        User currentUser = getCurrentUser();
        return expenseRepository.getMonthlySummary(currentUser,year);
    }


    @Override
    public YearlySummaryDto getYearlySummary(int year) {
        User currentUser = getCurrentUser();
        Double totalExpense = expenseRepository.getYearlyTotal(currentUser,year);
        return new YearlySummaryDto(year, totalExpense);
    }
}
