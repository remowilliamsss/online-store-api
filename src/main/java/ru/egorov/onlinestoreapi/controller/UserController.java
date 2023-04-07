package ru.egorov.onlinestoreapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.egorov.onlinestoreapi.dto.*;
import ru.egorov.onlinestoreapi.exception.BadCredentialsException;
import ru.egorov.onlinestoreapi.exception.ProductNotAddedOrUpdatedException;
import ru.egorov.onlinestoreapi.exception.UserNotFoundException;
import ru.egorov.onlinestoreapi.mapper.*;
import ru.egorov.onlinestoreapi.model.CartPosition;
import ru.egorov.onlinestoreapi.model.Product;
import ru.egorov.onlinestoreapi.model.User;
import ru.egorov.onlinestoreapi.security.PasswordEncoder;
import ru.egorov.onlinestoreapi.service.UserService;
import ru.egorov.onlinestoreapi.validator.CartPositionValidator;
import ru.egorov.onlinestoreapi.validator.UserCredentialsValidator;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.NoSuchElementException;
import java.util.Set;

import static ru.egorov.onlinestoreapi.util.ErrorMessageBuilder.getErrorMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final ProductMapper productMapper;
    private final ProductSetMapper productSetMapper;
    private final CartPositionMapper cartPositionMapper;
    private final CartPositionSetMapper cartPositionSetMapper;
    private final PasswordEncoder encoder;
    private final UserCredentialsValidator validator;
    private final CartPositionValidator cartPositionValidator;

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

        String name = credentials.getName();

        try {
            User user = userService.find(name);

            if (encoder.authenticate(credentials.getPassword(), user.getPassword(), user.getSalt())) {

                return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);

            } else {
                throw new BadCredentialsException("Wrong password!");
            }

        } catch (NoSuchElementException e) {
            throw new BadCredentialsException(String.format("User with name \"%s\" doesn't exist!", name));
        }
    }

    @GetMapping("/{id}/favorites")
    public ResponseEntity<Set<ProductDto>> favorites(@PathVariable("id") Integer id) {
        try {
            Set<Product> products = userService.find(id)
                    .getFavorites();

            return new ResponseEntity<>(productSetMapper.toDtoSet(products), HttpStatus.OK);

        } catch (NoSuchElementException e) {
            throw new UserNotFoundException(String.format("User with id %d doesn't exist!", id));
        }
    }

    @PostMapping("/{id}/favorites/add")
    public ResponseEntity<ProductDto> addFavorite(@PathVariable("id") Integer id,
            @RequestBody ProductDto productDto) {

        try {
            Product product = productMapper.toEntity(productDto);
            product = userService.addFavorite(id, product);

            return new ResponseEntity<>(productMapper.toDto(product), HttpStatus.OK);

        } catch (NoSuchElementException e) {
            throw new ProductNotAddedOrUpdatedException("User or product doesn't exist!");
        }
    }

    @DeleteMapping("/{id}/favorites/remove")
    public ResponseEntity<HttpStatus> removeFavorite(@PathVariable("id") Integer id,
                                                  @RequestBody ProductDto productDto) {

        try {
            Product product = productMapper.toEntity(productDto);
            userService.removeFavorite(id, product);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (NoSuchElementException e) {
            throw new ProductNotAddedOrUpdatedException("User or product doesn't exist!");
        }
    }

    @GetMapping("/{id}/cart")
    public ResponseEntity<Set<CartPositionDto>> cart(@PathVariable("id") Integer id) {
        try {
            Set<CartPosition> cartPositions = userService.find(id)
                    .getCart()
                    .getPositions();

            return new ResponseEntity<>(cartPositionSetMapper.toDtoSet(cartPositions), HttpStatus.OK);

        } catch (NoSuchElementException e) {
            throw new UserNotFoundException(String.format("User with id %d doesn't exist!", id));
        }
    }

    @PostMapping("/{id}/cart/add")
    public ResponseEntity<CartPositionDto> addToCart(@PathVariable("id") Integer id,
                                                @RequestBody @Valid CartPositionDto cartPositionDto,
                                                                        BindingResult bindingResult) {

        CartPosition cartPosition = cartPositionMapper.toEntity(cartPositionDto);
        cartPositionValidator.validate(cartPosition, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ProductNotAddedOrUpdatedException(getErrorMessage(bindingResult));
        }

        try {
            cartPosition = userService.addToCart(id, cartPosition);

            return new ResponseEntity<>(cartPositionMapper.toDto(cartPosition), HttpStatus.OK);

        } catch (NoSuchElementException e) {
            throw new ProductNotAddedOrUpdatedException(String.format("User with id %d doesn't exist!", id));
        }
    }

    @DeleteMapping("/{id}/cart/remove")
    public ResponseEntity<HttpStatus> removeFromCart(@PathVariable("id") Integer id,
                                                     @RequestBody CartPositionDto cartPositionDto) {
        try {
            CartPosition cartPosition = cartPositionMapper.toEntity(cartPositionDto);
            userService.removeFromCart(id, cartPosition);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (NoSuchElementException e) {
            throw new ProductNotAddedOrUpdatedException("User or product doesn't exist!");
        }
    }
}
