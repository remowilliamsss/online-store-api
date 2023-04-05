package ru.egorov.onlinestoreapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCredentials {

    @NotEmpty
    private String username;

    @Size(max = 128)
    @NotEmpty
    private String password;

    private Boolean isAdmin;
}
