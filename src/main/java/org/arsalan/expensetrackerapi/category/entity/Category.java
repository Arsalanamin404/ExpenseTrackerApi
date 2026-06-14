package org.arsalan.expensetrackerapi.category.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.arsalan.expensetrackerapi.auth.entity.User;
import org.arsalan.expensetrackerapi.expense.entity.Expense;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "categories",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "name"})
        }
)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Expense> expenses = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Category(String name) {
        this.name = name;
    }

    @PrePersist
    @PreUpdate
    public void formatFields() {
        if (name != null) {
            name = name.trim().toLowerCase();
        }
    }
}
