package com.example.aisa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "recipes")
@NoArgsConstructor
@Getter
@ToString
public class RecipeElement {

    @EmbeddedId
    @Setter
    private RecipeKey id = new RecipeKey();

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapsId("drinkId")
    @JoinColumn(name = "drink_id")
    private Drink drink;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column(name = "amount")
    @Setter
    private Integer amount;

    public RecipeElement(Drink drink, Ingredient ingredient, Integer amount) {
        this.id = new RecipeKey(drink.getId(), ingredient.getId());
        this.drink = drink;
        this.ingredient = ingredient;
        this.amount = amount;
    }

    public void setId(Drink drink, Ingredient ingredient) {
        this.id = new RecipeKey(drink.getId(), ingredient.getId());
        this.drink = drink;
        this.ingredient = ingredient;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
        this.id.setDrinkId(drink.getId());
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
        this.id.setIngredientId(id.getIngredientId());
    }
}
