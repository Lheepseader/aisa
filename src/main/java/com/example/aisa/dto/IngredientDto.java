package com.example.aisa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class IngredientDto {
    @Positive(message = "Id must be positive")
    @Schema(description = "Ingredient id", example = "1")
    private Long id;
    @NotNull(message = "IngredientName must not be null")
    @Schema(description = "Ingredient name", example = "Молоко")
    private String ingredientName;
    @PositiveOrZero(message = "Leftover must not be null")
    @Schema(description = "Ingredient leftover", example = "500")
    private Integer leftover;
}
