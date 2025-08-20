package com.example.finances.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequest {
    @NotBlank
    public String name;
}
