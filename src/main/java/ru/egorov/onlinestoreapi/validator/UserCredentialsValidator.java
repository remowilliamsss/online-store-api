package ru.egorov.onlinestoreapi.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.egorov.onlinestoreapi.dto.UserCredentials;
import ru.egorov.onlinestoreapi.service.UserService;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class UserCredentialsValidator implements Validator {
    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserCredentials.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCredentials credentials = (UserCredentials) target;

        try {
            String name = credentials.getName();

            userService.find(name);

            errors.rejectValue("name", "",
                    String.format("user with name \"%s\" already exists", name));

        } catch (NoSuchElementException ignored) { // все ОК
        }
    }
}
