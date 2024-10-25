package com.example.aisa.service;

import com.example.aisa.dto.DrinkDto;
import com.example.aisa.dto.IngredientDto;
import com.example.aisa.dto.OrderDto;
import com.example.aisa.dto.RecipeElementDto;
import com.example.aisa.exception.NotFoundException;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Set;

public interface CoffeeMachineService {
    void saveOrUpdateRecipe(Set<RecipeElementDto> recipeIngredientsDto, DrinkDto drinkDto);

    DrinkDto getTopDrinkByOrderCount() throws NotFoundException;

    List<DrinkDto> getAllDrinks() throws NotFoundException;

    List<DrinkDto> getAllAvailableDrinks();

    List<DrinkDto> makeDrink(DrinkDto drinkDto);

    void updateIngredientLeftover(IngredientDto ingredientDto);

    List<OrderDto> getOrders();

    @Scheduled(cron = "0 0 1 * * ?") void removeOldStatistics();
}
