package ru.egorov.onlinestoreapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private int id;

    @NotBlank
    private String name;

    private Boolean candle;

    private Boolean diffuser;

    private Boolean autodiffuser;

    private Boolean isNew;

    private Boolean isHit;

    private Boolean isSale;

    private Double price;

    private Integer amount;

    private String image;
}
