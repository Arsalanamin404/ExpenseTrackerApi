package org.arsalan.expensetrackerapi.repository;

import org.arsalan.expensetrackerapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
