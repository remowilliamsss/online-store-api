package ru.egorov.onlinestoreapi.util;

import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class ErrorMessageBuilder {

    public static String getErrorMessage(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(error -> String.format("%s - %s;", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(" "));
    }
}
