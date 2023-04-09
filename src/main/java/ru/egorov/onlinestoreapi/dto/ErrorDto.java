package ru.egorov.onlinestoreapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Информация об ошибке")
public class ErrorDto {

    @Schema(description = "Сообщение ошибки")
    private String message;
}
