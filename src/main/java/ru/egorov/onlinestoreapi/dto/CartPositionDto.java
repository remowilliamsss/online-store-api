package ru.egorov.onlinestoreapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartPositionDto {

    int id;

    @NotNull
    private ProductDto product;

    @NotNull
    private Integer amount;
}
