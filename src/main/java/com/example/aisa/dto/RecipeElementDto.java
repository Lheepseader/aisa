package com.example.aisa.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RecipeElementDto {
    @NotNull(message = "drink must not be null")
    private DrinkDto drink;
    @NotNull(message = "ingredient must not be null")
    private IngredientDto ingredient;
    @Positive(message = "amount must be positive")
    private Integer amount;
}
