package org.arsalan.expensetrackerapi.category.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.arsalan.expensetrackerapi.category.dto.CategoryRequestDto;
import org.arsalan.expensetrackerapi.category.entity.Category;
import org.arsalan.expensetrackerapi.category.service.ICategoryService;
import org.arsalan.expensetrackerapi.common.dto.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<Category>> createCategory(
            @Valid @RequestBody CategoryRequestDto dto,
            HttpServletRequest request
            ){
        Category category = categoryService.createCategory(dto);

        ApiResponse<Category> response = ApiResponse.success(
                HttpStatus.CREATED.value(),
                "Category created successfully",
                category,
                request.getRequestURI()
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Category>>> getAllCategories(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            HttpServletRequest request) {

        Page<Category> categories =
                categoryService.getAllCategories(pageNumber, pageSize);

        ApiResponse<Page<Category>> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "Categories fetched successfully",
                categories,
                request.getRequestURI()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(
            @PathVariable UUID id,
            HttpServletRequest request) {

        Category category = categoryService.getCategoryById(id);

        ApiResponse<Category> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "Category fetched successfully",
                category,
                request.getRequestURI()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(
            @PathVariable UUID id,
            @Valid @RequestBody CategoryRequestDto dto,
            HttpServletRequest request) {

        Category category = categoryService.updateCategory(id, dto);

        ApiResponse<Category> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "Category updated successfully",
                category,
                request.getRequestURI()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(
            @PathVariable UUID id,
            HttpServletRequest request) {

        categoryService.deleteCategory(id);

        ApiResponse<Void> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "Category with id " + id + " deleted successfully",
                null,
                request.getRequestURI()
        );

        return ResponseEntity.ok(response);
    }
}
