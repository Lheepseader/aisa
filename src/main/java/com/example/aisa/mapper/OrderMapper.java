package com.example.aisa.mapper;

import com.example.aisa.dto.OrderDto;
import com.example.aisa.entity.Order;
import org.mapstruct.*;

@Named("OrderMapper")
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    OrderDto toDto(Order order);

    @InheritInverseConfiguration
    @Mapping(target = "drink.recipeElements", ignore = true)
    Order toEntity(OrderDto orderDto);
}
