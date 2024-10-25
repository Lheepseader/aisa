package com.example.aisa.exception;

import com.example.aisa.dto.IngredientDto;
import lombok.Getter;

import java.util.Map;

@Getter
public class NotEnoughIngredientException extends RuntimeException {
    private final Map<IngredientDto, Integer> notAvailableIngredientsAndShortage;

    public NotEnoughIngredientException(Map<IngredientDto, Integer> notAvailableIngredientsAndShortage) {

        super("Not enough ingredient and shortage: " + notAvailableIngredientsAndShortage);
        this.notAvailableIngredientsAndShortage = notAvailableIngredientsAndShortage;
    }
}
