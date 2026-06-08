package org.arsalan.expensetrackerapi.repository;

import org.arsalan.expensetrackerapi.dto.CategoryExpenseDto;
import org.arsalan.expensetrackerapi.dto.MonthlyCategoryExpenseDto;
import org.arsalan.expensetrackerapi.dto.MonthlyExpenseSummaryDto;
import org.arsalan.expensetrackerapi.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    // todo: fetch expense amount by category names
    @Query("""
            SELECT new org.arsalan.expensetrackerapi.dto.CategoryExpenseDto(
                c.name,
                SUM(e.amount)
            )
            FROM Expense e
            JOIN e.category c
            GROUP BY c.name
            ORDER BY SUM(e.amount) DESC
            """)
    Page<CategoryExpenseDto> getTotalExpenseByAllCategories(Pageable pageable);

    // TODO: Fetch expenses by category name (search via Category relation)
    Page<Expense> findByCategory_NameIgnoreCase(String categoryName,
                                                Pageable pageable);


    // TODO: Fetch expenses between given dates
    Page<Expense> findByDateBetween(
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );

    @Query("""
            SELECT e
            FROM Expense e
            WHERE YEAR(e.date) = :year
            AND MONTH(e.date) = :month
            """)
    Page<Expense> getMonthlyExpenses(
            int year,
            int month,
            Pageable pageable
    );


    // coalesce returns 0 if not found
    @Query("""
                    SELECT COALESCE(SUM(e.amount),0)
                    FROM Expense e
                    WHERE YEAR(e.date)=:year
                    AND MONTH(e.date)=:month
            """)
    Double getMonthlyTotal(
            int year,
            int month
    );

    // TODO: Fetch category-wise expense totals for a specific month
    @Query("""
            SELECT
                c.name AS categoryName,
                SUM(e.amount) AS totalAmount
            FROM Expense e
            JOIN e.category c
            WHERE e.date BETWEEN :startDate AND :endDate
            GROUP BY c.name
            ORDER BY SUM(e.amount) DESC
            """)
    Page<MonthlyCategoryExpenseDto> getMonthlyCategoryTotals(
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );

    // TODO: Fetch month-wise expense totals for a given year
    @Query("""
            SELECT
                EXTRACT(MONTH FROM e.date) as month,
                SUM(e.amount) as totalExpense
            FROM Expense e
            WHERE EXTRACT(YEAR FROM e.date) = :year
            GROUP BY EXTRACT(MONTH FROM e.date)
            ORDER BY EXTRACT(MONTH FROM e.date)
            """)
    List<MonthlyExpenseSummaryDto> getMonthlySummary(
            @Param("year") int year
    );

    //TODO: Fetch total expense for an entire year
    @Query("""
            SELECT COALESCE(SUM(e.amount),0)
            FROM Expense e
            WHERE EXTRACT(YEAR FROM e.date) = :year
            """)
    Double getYearlyTotal(
            @Param("year") int year
    );
}
