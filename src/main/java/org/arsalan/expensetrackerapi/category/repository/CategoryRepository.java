package org.arsalan.expensetrackerapi.category.repository;


import org.arsalan.expensetrackerapi.auth.entity.User;
import org.arsalan.expensetrackerapi.category.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByIdAndUser(
            UUID id,
            User user
    );

    Page<Category> findByUser(
            User user,
            Pageable pageable
    );

    Optional<Category> findByNameIgnoreCaseAndUser(
            String name,
            User user
    );

}
