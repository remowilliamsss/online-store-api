package ru.egorov.onlinestoreapi.mapper;

import org.mapstruct.Mapper;
import ru.egorov.onlinestoreapi.dto.ShoppingCartDto;
import ru.egorov.onlinestoreapi.model.ShoppingCart;

@Mapper(componentModel = "spring", uses = {CartProductSetMapper.class, UserMapper.class})
public interface ShoppingCartMapper {

    ShoppingCartDto toDto(ShoppingCart shoppingCart);
}
