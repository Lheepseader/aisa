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
public class DrinkDto {
    @Positive(message = "Id must be positive")
    @Schema(description = "Drink id", example = "3")
    private Long id;
    @NotNull(message = "DrinkName must not be null")
    @Schema(description = "Drink name", example = "Капучино")
    private String drinkName;
    @Positive(message = "Price must be positive")
    @Schema(description = "Drink price", example = "300")
    private Integer price;
    @PositiveOrZero(message = "OrderCount must be positive or zero")
    @Schema(description = "Number of drink orders", example = "10", defaultValue = "0")
    private Long orderCount = 0L;

    public DrinkDto(Long id, String drinkName, Integer price) {
        this.id = id;
        this.drinkName = drinkName;
        this.price = price;
    }
}
