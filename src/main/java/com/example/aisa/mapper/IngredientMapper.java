package com.example.aisa.mapper;

import com.example.aisa.dto.IngredientDto;
import com.example.aisa.entity.Ingredient;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Named("IngredientMapper")
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IngredientMapper {


    IngredientDto toDto(Ingredient ingredient);

    @InheritInverseConfiguration
    Ingredient toEntity(IngredientDto ingredientDto);
}
