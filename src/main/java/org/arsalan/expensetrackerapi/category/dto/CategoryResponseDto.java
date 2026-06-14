package org.arsalan.expensetrackerapi.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CategoryResponseDto {

    private UUID id;
    private String name;
}