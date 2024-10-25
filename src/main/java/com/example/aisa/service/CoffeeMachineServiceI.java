package com.example.aisa.service;

import com.example.aisa.dto.DrinkDto;
import com.example.aisa.dto.IngredientDto;
import com.example.aisa.dto.OrderDto;
import com.example.aisa.dto.RecipeElementDto;
import com.example.aisa.entity.Drink;
import com.example.aisa.entity.Ingredient;
import com.example.aisa.entity.Order;
import com.example.aisa.entity.RecipeElement;
import com.example.aisa.exception.NotEnoughIngredientException;
import com.example.aisa.exception.NotFoundException;
import com.example.aisa.mapper.DrinkMapper;
import com.example.aisa.mapper.IngredientMapper;
import com.example.aisa.mapper.OrderMapper;
import com.example.aisa.mapper.RecipeMapper;
import com.example.aisa.repository.DrinkRepository;
import com.example.aisa.repository.IngredientRepository;
import com.example.aisa.repository.OrderRepository;
import com.example.aisa.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CoffeeMachineServiceI implements CoffeeMachineService {

    private final DrinkRepository drinkRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final DrinkMapper drinkMapper;
    private final RecipeMapper recipeMapper;
    private final IngredientMapper ingredientMapper;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final Map<Drink, List<Order>> drinkOrdersListMap = new HashMap<>();

    @Override
    @Transactional
    public void saveOrUpdateRecipe(Set<RecipeElementDto> recipesDto, DrinkDto drinkDto) {
        Drink drink = drinkMapper.toEntity(drinkDto);
        Optional<Drink> drinkOptional = drinkRepository.findOne(Example.of(drink));
        if (drinkOptional.isEmpty()) {
            drinkRepository.save(drink);
        } else {
            drink = drinkOptional.get();
        }
        for (RecipeElementDto recipeElementDto : recipesDto) {
            RecipeElement recipeElement = recipeMapper.toEntity(recipeElementDto);
            Ingredient ingredient = recipeElement.getIngredient();
            Optional<Ingredient> ingredientOptional = ingredientRepository.findOne(Example.of(ingredient));
            if (ingredientOptional.isEmpty()) {
                ingredientRepository.save(ingredient);
            } else {
                ingredient = ingredientOptional.get();
            }
            recipeElement.setId(drink, ingredient);
            recipeRepository.save(recipeElement);
        }
    }

    @Override
    public DrinkDto getTopDrinkByOrderCount() {
        Optional<Drink> drink = drinkRepository.findTopByOrderByOrderCount();
        if (drink.isEmpty()) {
            throw new NotFoundException(Drink.class.getSimpleName(), "does not exist");
        }
        return drinkMapper.toDto(drink.get());
    }


    @Override
    public List<DrinkDto> getAllDrinks() {
        List<Drink> drinks = drinkRepository.findAll();
        if (drinks.isEmpty()) {
            throw new NotFoundException(Drink.class.getSimpleName(), "does not exist");
        }
        return drinks.stream().map(drinkMapper::toDto).toList();
    }

    @Override
    public List<DrinkDto> getAllAvailableDrinks() {
        List<DrinkDto> drinksDto = getAllDrinks();
        drinksDto =
                drinksDto.stream()
                        .filter(drinkDto -> isPossibleToMakeADrink(getNotEnoughIngredientsAndShortageForDrink(drinkDto)))
                        .toList();
        return drinksDto;
    }

    private Map<Ingredient, Integer> getNotEnoughIngredientsAndShortageForDrink(DrinkDto drinkDto) {
        Optional<Drink> drinkOptional = drinkRepository.findOne(Example.of(drinkMapper.toEntity(drinkDto)));
        if (drinkOptional.isEmpty()) {
            throw new NotFoundException(Drink.class.getSimpleName(), "does not exist");
        }
        Map<Ingredient, Integer> notAvailableIngredientsAndShortage = new HashMap<>();
        Drink drink = drinkOptional.get();
        Set<RecipeElement> recipeElements = drink.getRecipeElements();
        for (RecipeElement recipeElement : recipeElements) {
            Ingredient ingredient = recipeElement.getIngredient();
            Integer amount = recipeElement.getAmount();
            Integer leftOver = ingredient.getLeftover();
            if (amount > leftOver) {
                notAvailableIngredientsAndShortage.put(ingredient, amount);
            }
        }
        return notAvailableIngredientsAndShortage;
    }

    private boolean isPossibleToMakeADrink(Map<Ingredient, Integer> notAvailableIngredientsAndShortage) {
        return notAvailableIngredientsAndShortage.isEmpty();
    }

    @Override
    @Transactional
    public List<DrinkDto> makeDrink(DrinkDto drinkDto) {
        Map<Ingredient, Integer> notAvailableIngredientsAndShortage = getNotEnoughIngredientsAndShortageForDrink(
                drinkDto);
        boolean isPossible = isPossibleToMakeADrink(notAvailableIngredientsAndShortage);
        if (!isPossible) {
            Map<IngredientDto, Integer> notAvailableIngredientsAndShortageDto = new HashMap<>();
            for (Map.Entry<Ingredient, Integer> entry : notAvailableIngredientsAndShortage.entrySet()) {
                notAvailableIngredientsAndShortageDto.put(ingredientMapper.toDto(entry.getKey()), entry.getValue());
            }
            throw new NotEnoughIngredientException(notAvailableIngredientsAndShortageDto);
        }

        Drink drink = drinkRepository.findByDrinkName(drinkDto.getDrinkName());
        for (RecipeElement recipeElement : drink.getRecipeElements()) {
            Ingredient ingredient = recipeElement.getIngredient();
            ingredient.setLeftover(ingredient.getLeftover() - recipeElement.getAmount());
            ingredientRepository.save(ingredient);
        }
        Order order = new Order();
        order.setDrink(drink);
        order.setOrderTime(LocalDateTime.now());
        List<Order> orders = drinkOrdersListMap.getOrDefault(drink, new ArrayList<>());
        orders.add(order);
        drinkOrdersListMap.put(drink, orders);
        int count = 0;
        for (List<Order> list : drinkOrdersListMap.values()) {
            count += list.size();
        }
        if (count > 30) {
            saveOrders();
        }
        return getAllAvailableDrinks();
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void saveOrders() {
        for (Map.Entry<Drink, List<Order>> drinkOrderEntry : drinkOrdersListMap.entrySet()) {
            Drink drink = drinkOrderEntry.getKey();
            drink.setOrderCount(drink.getOrderCount() + drinkOrderEntry.getValue().size());
            if (drinkOrderEntry.getValue().isEmpty()) {
                continue;
            }
            orderRepository.saveAll(drinkOrderEntry.getValue());
        }
        drinkOrdersListMap.clear();
    }

    @Override
    public void updateIngredientLeftover(IngredientDto ingredientDto) {
        if (!ingredientRepository.existsByIngredientName(ingredientDto.getIngredientName())) {
            throw new NotFoundException(Ingredient.class.getSimpleName(), "does not exist");
        }
        Ingredient ingredient = ingredientRepository.findByIngredientName(ingredientDto.getIngredientName());
        ingredient.setLeftover(ingredientDto.getLeftover());
        ingredientRepository.save(ingredient);
    }

    @Override
    public List<OrderDto> getOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new NotFoundException(Order.class.getSimpleName(), "does not exist");
        }

        return orders.stream().map(orderMapper::toDto).toList();
    }

    @Override
    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional
    public void removeOldStatistics() {
        LocalDateTime thresholdDate = LocalDateTime.now().minusYears(5);
        List<Order> orders = orderRepository.findByOrderTimeBefore(thresholdDate);
        if (orders.isEmpty()) {
            return;
        }
        orderRepository.deleteAll(orders);
    }

}
