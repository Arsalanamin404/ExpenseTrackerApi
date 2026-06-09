package org.arsalan.expensetrackerapi.category.repository;


import org.arsalan.expensetrackerapi.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
