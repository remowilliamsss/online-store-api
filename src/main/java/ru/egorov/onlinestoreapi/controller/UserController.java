package ru.egorov.onlinestoreapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.egorov.onlinestoreapi.dto.UserCredentials;
import ru.egorov.onlinestoreapi.dto.UserDto;
import ru.egorov.onlinestoreapi.exception.BadCredentialsException;
import ru.egorov.onlinestoreapi.mapper.UserMapper;
import ru.egorov.onlinestoreapi.model.User;
import ru.egorov.onlinestoreapi.security.PasswordEncoder;
import ru.egorov.onlinestoreapi.service.UserService;
import ru.egorov.onlinestoreapi.validator.UserCredentialsValidator;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final UserCredentialsValidator validator;

    @PostMapping("/registration")
    public UserDto registration(@RequestBody @Valid UserCredentials credentials, BindingResult bindingResult)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        validator.validate(credentials, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BadCredentialsException(getErrorMessage(bindingResult));
        }

        byte[] salt = encoder.generateSalt();

        User user = new User(credentials.getName(), encoder.getEncryptedPassword(credentials
                .getPassword(), salt), salt, credentials.getIsAdmin());

        user = userService.create(user);

        return userMapper.toDto(user);
    }

    @PostMapping("/auth")
    public UserDto auth(@RequestBody UserCredentials credentials) throws NoSuchAlgorithmException,
            InvalidKeySpecException {

        try {
            User user = userService.find(credentials.getName());

            if (encoder.authenticate(credentials.getPassword(), user.getPassword(), user.getSalt())) {

                return userMapper.toDto(user);

            } else {
                throw new BadCredentialsException("Wrong password!");
            }

        } catch (NoSuchElementException e) {
            throw new BadCredentialsException("User with this name doesn't exist!");
        }
    }

    private String getErrorMessage(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(error -> String.format("%s - %s;", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(" "));
    }
}
