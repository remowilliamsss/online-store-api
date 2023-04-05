package ru.egorov.onlinestoreapi.mapper;

import org.mapstruct.Mapper;
import ru.egorov.onlinestoreapi.dto.CartProductDto;
import ru.egorov.onlinestoreapi.model.CartProduct;

import java.util.Set;

@Mapper(componentModel = "spring", uses = CartProductMapper.class)
public interface CartProductSetMapper {

    Set<CartProductDto> toDtoSet(Set<CartProduct> cartProducts);
}
