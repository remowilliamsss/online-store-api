package ru.egorov.onlinestoreapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.egorov.onlinestoreapi.model.Product;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDto {

    private int id;

    @NotEmpty
    @Size(max = 128)
    private String name;

    private Boolean isAdmin;

    private Set<Product> favorites = new HashSet<>();
}
