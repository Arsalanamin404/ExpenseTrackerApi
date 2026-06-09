package org.arsalan.expensetrackerapi.category.service;


import org.arsalan.expensetrackerapi.category.dto.CategoryRequestDto;
import org.arsalan.expensetrackerapi.category.entity.Category;
import org.arsalan.expensetrackerapi.category.repository.CategoryRepository;
import org.arsalan.expensetrackerapi.common.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    private Category findCategoryOrThrow(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(CategoryRequestDto categoryRequestDto) {
        Category category = new Category();
        category.setName(categoryRequestDto.getName());
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(UUID categoryId) {
        return findCategoryOrThrow(categoryId);
    }

    @Override
    public Page<Category> getAllCategories(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        return  categoryRepository.findAll(pageable);
    }

    @Override
    public Category updateCategory(UUID categoryId, CategoryRequestDto categoryRequestDto) {
        Category category = findCategoryOrThrow(categoryId);
        category.setName(categoryRequestDto.getName());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        Category category = findCategoryOrThrow(categoryId);
        categoryRepository.delete(category);

    }
}
