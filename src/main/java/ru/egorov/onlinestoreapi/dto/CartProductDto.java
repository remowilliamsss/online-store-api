package ru.egorov.onlinestoreapi.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.egorov.onlinestoreapi.model.Product;

@Getter
@Setter
public class CartProductDto {

    private Product product;

    @Size(min = 1)
    private Integer amount;
}
