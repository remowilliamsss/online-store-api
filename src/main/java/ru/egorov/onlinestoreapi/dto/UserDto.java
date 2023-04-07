package ru.egorov.onlinestoreapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private int id;

    @NotBlank
    @Size(max = 256)
    private String name;

    private Boolean isAdmin;
}
