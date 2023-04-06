package ru.egorov.onlinestoreapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCredentials {

    @NotBlank
    private String name;

    @Size(max = 256)
    @NotEmpty
    private String password;

    private Boolean isAdmin;
}
