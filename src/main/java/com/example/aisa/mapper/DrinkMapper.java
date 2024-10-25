package com.example.aisa.mapper;

import com.example.aisa.dto.DrinkDto;
import com.example.aisa.entity.Drink;
import org.mapstruct.*;

@Named("DrinkMapper")
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DrinkMapper {

    DrinkDto toDto(Drink drink);

    @Named("toEntity")
    @InheritInverseConfiguration
    @Mapping(target = "recipeElements", ignore = true)
    Drink toEntity(DrinkDto drinkDto);
}
