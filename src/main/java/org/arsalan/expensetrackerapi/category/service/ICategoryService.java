package org.arsalan.expensetrackerapi.category.service;


import org.arsalan.expensetrackerapi.category.dto.CategoryRequestDto;
import org.arsalan.expensetrackerapi.category.dto.CategoryResponseDto;
import org.arsalan.expensetrackerapi.category.entity.Category;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ICategoryService {

    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);

    CategoryResponseDto getCategoryById(UUID categoryId);

    Page<CategoryResponseDto> getAllCategories(int pageNumber, int pageSize);

    CategoryResponseDto updateCategory(UUID categoryId,
                                       CategoryRequestDto categoryRequestDto);

    void deleteCategory(UUID categoryId);
}