package ru.egorov.onlinestoreapi.mapper;

import org.mapstruct.Mapper;
import ru.egorov.onlinestoreapi.dto.CartProductDto;
import ru.egorov.onlinestoreapi.model.CartProduct;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface CartProductMapper {

    CartProductDto toDto(CartProduct cartProduct);
}
