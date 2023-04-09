package ru.egorov.onlinestoreapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Информация о пользователе для регистрации и аутентификации")
public class UserCredentials {

    @NotBlank
    @Schema(description = "Имя (логин) пользователя")
    private String name;

    @NotEmpty
    @Size(max = 256)
    @Schema(description = "Пароль пользователя")
    private String password;

    @Schema(description = "Является ли пользователь админом")
    private Boolean isAdmin;
}
