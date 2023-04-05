package ru.egorov.onlinestoreapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.egorov.onlinestoreapi.dto.UserCredentials;
import ru.egorov.onlinestoreapi.dto.UserDto;
import ru.egorov.onlinestoreapi.exception.BadCredentialsException;
import ru.egorov.onlinestoreapi.mapper.UserMapper;
import ru.egorov.onlinestoreapi.model.User;
import ru.egorov.onlinestoreapi.security.PasswordEncoder;
import ru.egorov.onlinestoreapi.service.UserService;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    @PostMapping("/registration")
    public UserDto registration(@RequestBody UserCredentials credentials) throws NoSuchAlgorithmException,
            InvalidKeySpecException {

        byte[] salt = encoder.generateSalt();

        User user = new User(credentials.getUsername(), encoder.getEncryptedPassword(credentials
                .getPassword(), salt), salt, credentials.getIsAdmin());

        System.out.println(credentials.getIsAdmin());

        user = userService.create(user);

        return userMapper.toDto(user);
    }

    @PostMapping("/auth")
    public UserDto auth(@RequestBody UserCredentials credentials) throws NoSuchAlgorithmException,
            InvalidKeySpecException {

        User user = userService.find(credentials.getUsername());

        if (encoder.authenticate(credentials.getPassword(), user.getPassword(), user.getSalt())) {
            return userMapper.toDto(user);
        } else {
            throw new BadCredentialsException("Wrong password!");
        }
    }
}
