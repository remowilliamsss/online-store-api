package ru.egorov.onlinestoreapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Информация о товаре")
public class ProductDto {

    @Schema(description = "Идентификатор товара")
    private int id;

    @Schema(description = "Название товара")
    @NotBlank
    private String name;

    @Schema(description = "Относится ли товар к категории \"Ароматические свечи\"")
    private Boolean candle;

    @Schema(description = "Относится ли товар к категории \"Диффузоры\"")
    private Boolean diffuser;

    @Schema(description = "Относится ли товар к категории \"Автоматические диффузоры\"")
    private Boolean autodiffuser;

    @Schema(description = "Относится ли товар к подкатегории \"Новинки\"")
    private Boolean isNew;

    @Schema(description = "Относится ли товар к подкатегории \"Хиты\"")
    private Boolean isHit;

    @Schema(description = "Относится ли товар к подкатегории \"Акции\"")
    private Boolean isSale;

    @Schema(description = "Цена товара")
    private Double price;

    @Schema(description = "Количество товара")
    private Integer amount;

    @Schema(description = "url с изображением товара")
    private String image;
}
