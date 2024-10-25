package com.example.aisa.config;

import com.example.aisa.dto.DrinkDto;
import com.example.aisa.dto.IngredientDto;
import com.example.aisa.dto.RecipeElementDto;
import com.example.aisa.repository.DrinkRepository;
import com.example.aisa.service.CoffeeMachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CoffeeMachineService coffeeMachineService;
    private final DrinkRepository drinkRepository;


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        DrinkDto espresso = new DrinkDto(null, "Эспрессо", 200);
        DrinkDto americano = new DrinkDto(null, "Американо", 250);
        DrinkDto cappuccino = new DrinkDto(null, "Капучино", 300);
        IngredientDto coffee = new IngredientDto(null, "Кофе", 500);
        IngredientDto water = new IngredientDto(null, "Вода", 2000);
        IngredientDto milkFoam = new IngredientDto(null, "Молочная пена", 100);
        IngredientDto milk = new IngredientDto(null, "Молоко", 2000);

        Set<RecipeElementDto> recipe1 = new HashSet<>();
        RecipeElementDto recipeElementEspresso1 = new RecipeElementDto(espresso, coffee, 10);
        RecipeElementDto recipeElementEspresso2 = new RecipeElementDto(espresso, water, 30);
        recipe1.add(recipeElementEspresso1);
        recipe1.add(recipeElementEspresso2);
        if (!drinkRepository.existsByDrinkName(espresso.getDrinkName())) {
            coffeeMachineService.saveOrUpdateRecipe(recipe1, espresso);
        }
        Set<RecipeElementDto> recipe2 = new HashSet<>();
        RecipeElementDto recipeElementAmericano1 = new RecipeElementDto(americano, coffee, 30);
        RecipeElementDto recipeElementAmericano2 = new RecipeElementDto(americano, coffee, 120);
        recipe2.add(recipeElementAmericano1);
        recipe2.add(recipeElementAmericano2);
        if (!drinkRepository.existsByDrinkName(americano.getDrinkName())) {
            coffeeMachineService.saveOrUpdateRecipe(recipe2, americano);
        }
        Set<RecipeElementDto> recipe3 = new HashSet<>();
        RecipeElementDto recipeElementCappuccino1 = new RecipeElementDto(cappuccino, coffee, 10);
        RecipeElementDto recipeElementCappuccino2 = new RecipeElementDto(cappuccino, milk, 120);
        RecipeElementDto recipeElementCappuccino3 = new RecipeElementDto(cappuccino, milkFoam, 1);
        recipe3.add(recipeElementCappuccino1);
        recipe3.add(recipeElementCappuccino2);
        recipe3.add(recipeElementCappuccino3);
        if (!drinkRepository.existsByDrinkName(cappuccino.getDrinkName())) {
            coffeeMachineService.saveOrUpdateRecipe(recipe3, cappuccino);
        }
    }
}
