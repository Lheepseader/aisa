package com.example.aisa.mapper;

import com.example.aisa.dto.RecipeElementDto;
import com.example.aisa.entity.RecipeElement;
import com.example.aisa.entity.RecipeKey;
import org.mapstruct.*;

@Named("RecipeMapper")
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = DrinkMapper.class)
public interface RecipeMapper {

    @Mapping(source = "drink", target = "drink")
    @Mapping(source = "ingredient", target = "ingredient")
    RecipeElementDto toDto(RecipeElement recipeElement);

    @InheritInverseConfiguration
    @Mapping(target = "id", expression = "java(toRecipeKey(recipeElementDto))")
    @Mapping(target = "drink", qualifiedByName = {"DrinkMapper", "toEntity"})
    RecipeElement toEntity(RecipeElementDto recipeElementDto);

    default RecipeKey toRecipeKey(RecipeElementDto recipeElementDto) {
        return new RecipeKey(recipeElementDto.getDrink().getId(), recipeElementDto.getIngredient().getId());
    }


}
