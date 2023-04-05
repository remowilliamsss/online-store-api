package ru.egorov.onlinestoreapi.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private int id;

    @NotEmpty
    private String name;

    private String category;

    private String subcategory;

    private Double price;

    private Integer amount;

    private String image;
}
