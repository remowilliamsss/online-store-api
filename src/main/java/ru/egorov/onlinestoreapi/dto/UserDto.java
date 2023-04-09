package ru.egorov.onlinestoreapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Информация о пользователе")
public class UserDto {

    @Schema(description = "Идентификатор пользователя")
    private int id;

    @NotBlank
    @Size(max = 256)
    @Schema(description = "Имя (логин) пользователя")
    private String name;

    @Schema(description = "Является ли пользователь админом")
    private Boolean isAdmin;
}
