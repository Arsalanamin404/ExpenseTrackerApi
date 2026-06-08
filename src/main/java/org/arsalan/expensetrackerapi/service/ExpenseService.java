package org.arsalan.expensetrackerapi.service;

import org.arsalan.expensetrackerapi.dto.*;
import org.arsalan.expensetrackerapi.entity.Category;
import org.arsalan.expensetrackerapi.entity.Expense;
import org.arsalan.expensetrackerapi.exception.ResourceNotFoundException;
import org.arsalan.expensetrackerapi.repository.CategoryRepository;
import org.arsalan.expensetrackerapi.repository.ExpenseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ExpenseService implements IExpenseService {
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseService(ExpenseRepository expenseRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
    }

    private Expense findExpenseOrThrow(UUID id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id " + id));
    }

    private Category findCategoryOrThrow(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }

    private ExpenseResponseDto mapToDto(Expense expense) {

        return new ExpenseResponseDto(
                expense.getId(),
                expense.getTitle(),
                expense.getAmount(),
                expense.getCategory().getId(),
                expense.getCategory().getName(),
                expense.getDate()
        );
    }

    @Override
    public ExpenseResponseDto createExpense(CreateExpenseRequestDto dto) {
        Expense expense = new Expense();
        Category category = findCategoryOrThrow(dto.getCategory_id());

        expense.setTitle(dto.getTitle());
        expense.setAmount(dto.getAmount());
        expense.setCategory(category);
        expense.setDate(dto.getDate());
        return mapToDto(expenseRepository.save(expense));
    }

    @Override
    public Page<ExpenseResponseDto> getAllExpenses(int pageNumber, int pageSize, boolean asc) {
        Sort sort = asc
                ? Sort.by("amount").ascending()
                : Sort.by("amount").descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        return expenseRepository
                .findAll(pageable)
                .map(this::mapToDto);
    }

    @Override
    public Page<ExpenseResponseDto> getExpenseByCategory(String category, int pageNumber, int pageSize, boolean asc) {
        Sort sort = asc
                ? Sort.by("amount").ascending()
                : Sort.by("amount").descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        return expenseRepository
                .findByCategory_NameIgnoreCase(category, pageable)
                .map(this::mapToDto);
    }

    @Override
    public Page<CategoryExpenseDto> getTotalExpenseByAllCategories(
            int pageNumber,
            int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return expenseRepository.getTotalExpenseByAllCategories(pageable);
    }

    @Override
    public Page<ExpenseResponseDto> getExpensesBetweenDates(
            LocalDate startDate,
            LocalDate endDate,
            int pageNumber,
            int pageSize,
            boolean asc
    ) {

        Sort sort = asc
                ? Sort.by("amount").ascending()
                : Sort.by("amount").descending();

        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                sort
        );

        return expenseRepository
                .findByDateBetween(
                        startDate,
                        endDate,
                        pageable
                )
                .map(this::mapToDto);
    }

    @Override
    public Page<ExpenseResponseDto> getMonthlyExpenses(int year, int month, int pageNumber, int pageSize, boolean asc) {
        Sort sort = asc
                ? Sort.by("amount").ascending()
                : Sort.by("amount").descending();

        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                sort
        );

        return expenseRepository
                .getMonthlyExpenses(year, month, pageable)
                .map(this::mapToDto);
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
    public Page<CategoryExpenseDto> getCategorySummary(
            int pageNumber,
            int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return expenseRepository.getTotalExpenseByAllCategories(pageable);
    }

    @Override
    public YearlySummaryDto getYearlySummary(int year) {
        Double totalExpense = expenseRepository.getYearlyTotal(year);
        return new YearlySummaryDto(year,totalExpense);
    }

    @Override
    public ExpenseResponseDto getExpenseById(UUID id) {
        return mapToDto(findExpenseOrThrow(id));
    }

    @Override
    public ExpenseResponseDto updateExpenseById(UUID id, CreateExpenseRequestDto dto) {
        Expense expense = findExpenseOrThrow(id);
        Category category = findCategoryOrThrow(dto.getCategory_id());

        expense.setTitle(dto.getTitle());
        expense.setCategory(category);
        expense.setDate(dto.getDate());
        expense.setAmount(dto.getAmount());

        return mapToDto(expenseRepository.save(expense));
    }

    @Override
    public ExpenseResponseDto updateTitle(UUID id, UpdateTitleDto dto) {
        Expense expense = findExpenseOrThrow(id);
        expense.setTitle(dto.getTitle());
        return mapToDto(expenseRepository.save(expense));
    }

    @Override
    public ExpenseResponseDto updateAmount(UUID id, UpdateAmountDto dto) {
        Expense expense = findExpenseOrThrow(id);
        expense.setAmount(dto.getAmount());
        return mapToDto(expenseRepository.save(expense));
    }

    @Override
    public ExpenseResponseDto updateCategory(UUID id, UpdateCategoryDto dto) {
        Expense expense = findExpenseOrThrow(id);
        Category category = findCategoryOrThrow(dto.getCategory_id());

        expense.setCategory(category);
        return mapToDto(expenseRepository.save(expense));
    }

    @Override
    public ExpenseResponseDto updateDate(UUID id, UpdateDateDto dto) {
        Expense expense = findExpenseOrThrow(id);
        expense.setDate(dto.getDate());
        return mapToDto(expenseRepository.save(expense));
    }

    @Override
    public void deleteExpenseById(UUID id) {
        Expense expense = findExpenseOrThrow(id);
        expenseRepository.delete(expense);
    }
}
