package ru.egorov.onlinestoreapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Информация о товаре в корзине")
public class CartPositionDto {

    @Schema(description = "Идентификатор товара в корзине")
    int id;

    @NotNull
    private ProductDto product;

    @NotNull
    @Schema(description = "Количество данного товара в корзине")
    private Integer amount;
}
