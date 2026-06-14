package org.arsalan.expensetrackerapi.expense.repository;

import org.arsalan.expensetrackerapi.auth.entity.User;
import org.arsalan.expensetrackerapi.category.dto.CategoryExpenseDto;
import org.arsalan.expensetrackerapi.expense.entity.Expense;
import org.arsalan.expensetrackerapi.report.dto.MonthlyCategoryExpenseDto;
import org.arsalan.expensetrackerapi.report.dto.MonthlyExpenseSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    // ==========================
    // USER-SPECIFIC EXPENSES
    // ==========================

    // TODO: Fetch all expenses belonging to a specific user
    Page<Expense> findByUser(
            User user,
            Pageable pageable
    );

    // TODO: Fetch a specific expense only if it belongs to the given user
    Optional<Expense> findByIdAndUser(
            UUID id,
            User user
    );

    // TODO: Fetch user expenses filtered by category name
    Page<Expense> findByUserAndCategory_NameIgnoreCase(
            User user,
            String categoryName,
            Pageable pageable
    );

    // TODO: Fetch user expenses within a date range
    Page<Expense> findByUserAndDateBetween(
            User user,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );

    // TODO: Fetch user expenses for a specific month and year
    @Query("""
            SELECT e
            FROM Expense e
            WHERE e.user = :user
            AND YEAR(e.date) = :year
            AND MONTH(e.date) = :month
            """)
    Page<Expense> getMonthlyExpenses(
            @Param("user") User user,
            @Param("year") int year,
            @Param("month") int month,
            Pageable pageable
    );

    // ==========================
    // CATEGORY REPORTS
    // ==========================

    // TODO: Fetch total expense grouped by category
    @Query("""
            SELECT new org.arsalan.expensetrackerapi.category.dto.CategoryExpenseDto(
                c.name,
                SUM(e.amount)
            )
            FROM Expense e
            JOIN e.category c
            WHERE e.user = :user
            GROUP BY c.name
            ORDER BY SUM(e.amount) DESC
            """)
    Page<CategoryExpenseDto> getTotalExpenseByAllCategories(
            @Param("user") User user,
            Pageable pageable
    );

    // ==========================
    // MONTHLY REPORTS
    // ==========================

    // TODO: Fetch total expense amount for a specific month
    @Query("""
            SELECT COALESCE(SUM(e.amount),0)
            FROM Expense e
            WHERE e.user = :user
            AND YEAR(e.date) = :year
            AND MONTH(e.date) = :month
            """)
    Double getMonthlyTotal(
            @Param("user") User user,
            @Param("year") int year,
            @Param("month") int month
    );

    // TODO: Fetch category-wise expense totals for a specific period
    @Query("""
            SELECT
                c.name AS categoryName,
                SUM(e.amount) AS totalAmount
            FROM Expense e
            JOIN e.category c
            WHERE e.user = :user
            AND e.date BETWEEN :startDate AND :endDate
            GROUP BY c.name
            ORDER BY SUM(e.amount) DESC
            """)
    Page<MonthlyCategoryExpenseDto> getMonthlyCategoryTotals(
            @Param("user") User user,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );

    // TODO: Fetch month-wise expense summary for a given year
    @Query("""
            SELECT
                EXTRACT(MONTH FROM e.date) as month,
                SUM(e.amount) as totalExpense
            FROM Expense e
            WHERE e.user = :user
            AND EXTRACT(YEAR FROM e.date) = :year
            GROUP BY EXTRACT(MONTH FROM e.date)
            ORDER BY EXTRACT(MONTH FROM e.date)
            """)
    List<MonthlyExpenseSummaryDto> getMonthlySummary(
            @Param("user") User user,
            @Param("year") int year
    );

    // TODO: Fetch total expense amount for a specific year
    @Query("""
            SELECT COALESCE(SUM(e.amount),0)
            FROM Expense e
            WHERE e.user = :user
            AND EXTRACT(YEAR FROM e.date) = :year
            """)
    Double getYearlyTotal(
            @Param("user") User user,
            @Param("year") int year
    );
}