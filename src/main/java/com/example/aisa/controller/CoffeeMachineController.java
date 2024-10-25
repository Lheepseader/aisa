package com.example.aisa.controller;

import com.example.aisa.dto.DrinkDto;
import com.example.aisa.dto.IngredientDto;
import com.example.aisa.dto.RecipeElementDto;
import com.example.aisa.service.CoffeeMachineService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController("/api/drink")
@RequiredArgsConstructor
public class CoffeeMachineController {

    private final CoffeeMachineService coffeeMachineService;

    @PostMapping("/makeDrink")
    @Operation(
            summary = "Prepare a drink",
            description = "Takes information about a drink and returns a list of prepared drinks."
    )
    public ResponseEntity<List<DrinkDto>> makeDrink(@Valid @RequestBody DrinkDto drinkDto) {
        List<DrinkDto> drinksDto = coffeeMachineService.makeDrink(drinkDto);
        return ResponseEntity.ok(drinksDto);
    }

    @PostMapping("/addRecipe")
    @Operation(
            summary = "Add a drink recipe",
            description = "Accepts a new recipe and drink information for saving or updating."
    )
    public ResponseEntity<Void> addRecipe(@Valid @RequestBody Set<RecipeElementDto> recipeIngredientsDto,
                                          @Valid @RequestBody DrinkDto drinkDto) {
        coffeeMachineService.saveOrUpdateRecipe(recipeIngredientsDto, drinkDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getMostPopular")
    @Operation(
            summary = "Get the most popular drink",
            description = "Returns information about the drink with the highest order count."
    )
    public ResponseEntity<DrinkDto> getMostPopularDrink() {
        DrinkDto drinkDto = coffeeMachineService.getTopDrinkByOrderCount();
        return ResponseEntity.ok(drinkDto);
    }

    @PostMapping("/updateIngredientLeftover")
    @Operation(
            summary = "Update the amount of ingredient",
            description = "Receives updated ingredient data to update its quantity."
    )
    public ResponseEntity<Void> addIngredient(@RequestBody @Valid IngredientDto ingredientDto) {
        coffeeMachineService.updateIngredientLeftover(ingredientDto);
        return ResponseEntity.ok().build();
    }

}
