package com.example.aisa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDto {
    @Positive(message = "Id must be positive")
    @Schema(description = "Order id", example = "1")
    private Long id;
    @NotNull(message = "Drink must not be null")
    @Schema(
            description = "Details of the drink",
            example = """
                    {
                      "id": 1,
                      "drinkName": "Капучино",
                      "price": 100,
                      "orderCount": 10
                    }"""
    )
    private DrinkDto drink;
    @CreatedDate
    @Schema(description = "Order timestamp", example = "2024-10-25T19:39:50.00")
    private LocalDateTime orderTime;
}
