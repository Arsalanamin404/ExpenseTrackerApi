package org.arsalan.expensetrackerapi.category.service;


import org.arsalan.expensetrackerapi.auth.entity.User;
import org.arsalan.expensetrackerapi.auth.repository.UserRepository;
import org.arsalan.expensetrackerapi.category.dto.CategoryRequestDto;
import org.arsalan.expensetrackerapi.category.dto.CategoryResponseDto;
import org.arsalan.expensetrackerapi.category.entity.Category;
import org.arsalan.expensetrackerapi.category.repository.CategoryRepository;
import org.arsalan.expensetrackerapi.common.exception.ResourceAlreadyExistsException;
import org.arsalan.expensetrackerapi.common.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


    public CategoryServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    private CategoryResponseDto mapToDto(Category category) {
        return new CategoryResponseDto(
                category.getId(),
                category.getName()
        );
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();

        if (authentication == null) {
            throw new AuthenticationCredentialsNotFoundException("No authenticated user found");
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));
    }

    private Category findCategoryOrThrow(UUID id) {
        User currentUser = getCurrentUser();

        return categoryRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        User currentUser = getCurrentUser();

        categoryRepository
                .findByNameIgnoreCaseAndUser(
                        categoryRequestDto.getName(),
                        currentUser
                )
                .ifPresent(category -> {
                    throw new ResourceAlreadyExistsException(
                            "Category already exists"
                    );
                });

        Category category = new Category();
        category.setName(categoryRequestDto.getName());
        category.setUser(currentUser);

        return mapToDto(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDto getCategoryById(UUID categoryId) {
        return mapToDto(findCategoryOrThrow(categoryId));
    }

    @Override
    public Page<CategoryResponseDto> getAllCategories(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        User currentUser = getCurrentUser();


        return categoryRepository
                .findByUser(currentUser, pageable)
                .map(this::mapToDto);
    }

    @Override
    public CategoryResponseDto updateCategory(UUID categoryId, CategoryRequestDto categoryRequestDto) {
        User currentUser = getCurrentUser();

        Category category = findCategoryOrThrow(categoryId);

        categoryRepository
                .findByNameIgnoreCaseAndUser(
                        categoryRequestDto.getName(),
                        currentUser
                )
                .ifPresent(existingCategory -> {

                    if (!existingCategory.getId()
                            .equals(category.getId())) {

                        throw new ResourceAlreadyExistsException(
                                "Category already exists"
                        );
                    }
                });

        category.setName(categoryRequestDto.getName());

        return mapToDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        Category category = findCategoryOrThrow(categoryId);
        categoryRepository.delete(category);

    }
}
