package ru.egorov.onlinestoreapi.dto;

import lombok.Getter;
import lombok.Setter;
import ru.egorov.onlinestoreapi.model.User;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ShoppingCartDto {

    private User owner;

    private Set<CartProductDto> products = new HashSet<>();
}
