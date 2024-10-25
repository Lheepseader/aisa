package com.example.aisa.repository;

import com.example.aisa.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {
    boolean existsByDrinkName(String drinkName);

    Optional<Drink> findTopByOrderByOrderCount();

    Drink findByDrinkName(String drinkName);


}
