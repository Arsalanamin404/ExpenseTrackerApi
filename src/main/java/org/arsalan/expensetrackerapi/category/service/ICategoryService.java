package org.arsalan.expensetrackerapi.category.service;


import org.arsalan.expensetrackerapi.category.dto.CategoryRequestDto;
import org.arsalan.expensetrackerapi.category.entity.Category;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ICategoryService {

    Category createCategory(CategoryRequestDto categoryRequestDto);

    Category getCategoryById(UUID categoryId);

    Page<Category> getAllCategories(int pageNumber, int pageSize);

    Category updateCategory(UUID categoryId,
                                       CategoryRequestDto categoryRequestDto);

    void deleteCategory(UUID categoryId);
}