package com.example.aisa.repository;

import com.example.aisa.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    boolean existsByIngredientName(String ingredientName);

    Ingredient findByIngredientName(String ingredientName);

}
