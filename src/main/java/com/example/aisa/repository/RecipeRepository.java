package com.example.aisa.repository;

import com.example.aisa.entity.RecipeElement;
import com.example.aisa.entity.RecipeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeElement, RecipeKey> {
    List<RecipeElement> findById_DrinkId(Long drinkId);

}
