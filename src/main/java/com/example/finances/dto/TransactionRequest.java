package com.example.finances.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionRequest {
    @NotBlank public String description;
    @NotNull public BigDecimal value;
    @NotNull public LocalDate date;
    public Long categoryId; // nullable
    @NotBlank public String type; // INCOME or EXPENSE
}
