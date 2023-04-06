package ru.egorov.onlinestoreapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import static ru.egorov.onlinestoreapi.util.ErrorMessageBuilder.getErrorMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final UserCredentialsValidator validator;

    @PostMapping("/registration")
    public ResponseEntity<UserDto> registration(@RequestBody @Valid UserCredentials credentials, BindingResult bindingResult)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        validator.validate(credentials, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BadCredentialsException(getErrorMessage(bindingResult));
        }

        byte[] salt = encoder.generateSalt();

        User user = new User(credentials.getName(), encoder.getEncryptedPassword(credentials
                .getPassword(), salt), salt, credentials.getIsAdmin());

        user = userService.create(user);

        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
    }

    @PostMapping("/auth")
    public ResponseEntity<UserDto> auth(@RequestBody UserCredentials credentials) throws NoSuchAlgorithmException,
            InvalidKeySpecException {

        try {
            User user = userService.find(credentials.getName());

            if (encoder.authenticate(credentials.getPassword(), user.getPassword(), user.getSalt())) {

                return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);

            } else {
                throw new BadCredentialsException("Wrong password!");
            }

        } catch (NoSuchElementException e) {
            throw new BadCredentialsException("User with this name doesn't exist!");
        }
    }
}
