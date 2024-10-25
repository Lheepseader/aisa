package com.example.aisa.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RecipeKey implements Serializable {
    private Long drinkId;
    private Long ingredientId;

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RecipeKey that = (RecipeKey) o;
        return Objects.equals(drinkId, that.drinkId) && Objects.equals(ingredientId, that.ingredientId);
    }

    @Override public int hashCode() {
        return Objects.hash(drinkId, ingredientId);
    }
}
