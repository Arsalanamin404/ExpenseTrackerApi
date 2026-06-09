package org.arsalan.expensetrackerapi.expense.service;


import org.arsalan.expensetrackerapi.category.entity.Category;
import org.arsalan.expensetrackerapi.category.repository.CategoryRepository;

import org.arsalan.expensetrackerapi.common.exception.ResourceNotFoundException;


import org.arsalan.expensetrackerapi.expense.entity.Expense;
import org.arsalan.expensetrackerapi.expense.dto.CreateExpenseRequestDto;
import org.arsalan.expensetrackerapi.expense.dto.UpdateAmountDto;
import org.arsalan.expensetrackerapi.expense.dto.UpdateCategoryDto;
import org.arsalan.expensetrackerapi.expense.dto.UpdateDateDto;
import org.arsalan.expensetrackerapi.expense.dto.UpdateTitleDto;
import org.arsalan.expensetrackerapi.expense.dto.ExpenseResponseDto;
import org.arsalan.expensetrackerapi.expense.repository.ExpenseRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
