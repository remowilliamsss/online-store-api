package ru.egorov.onlinestoreapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.egorov.onlinestoreapi.model.CartPosition;
import ru.egorov.onlinestoreapi.model.Product;
import ru.egorov.onlinestoreapi.model.Cart;
import ru.egorov.onlinestoreapi.model.User;
import ru.egorov.onlinestoreapi.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ProductService productService;
    private final CartPositionService cartPositionService;
    private final CartService cartService;

    public User create(User user) {
        Cart cart = cartService.save(new Cart(user));
        user.setCart(cart);

        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public User find(Integer id) {
        return userRepository.findById(id)
                .orElseThrow();
    }

    public User find(String name) {
        return userRepository.findByName(name)
                .orElseThrow();
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public Product addFavorite(Integer userId, Product product) {
        User user = find(userId);
        product = productService.find(product.getId());


        product.getLikes()
                .add(user);
        user.getFavorites()
                .add(product);

        update(user);
        product = productService.save(product);

        return product;
    }

    public void removeFavorite(Integer userId, Product product) {
        User user = find(userId);
        product = productService.find(product.getId());

        product.getLikes()
                .remove(user);
        user.getFavorites()
                .remove(product);

        update(user);
        productService.save(product);
    }

    public CartPosition addToCart(Integer userId, CartPosition cartPosition) {
        User user = find(userId);
        Cart cart = user.getCart();
        Product product = productService.find(cartPosition
                .getProduct()
                .getId());

        cartPosition.setCart(cart);
        cartPosition.setProduct(product);
        cart.getPositions()
                .add(cartPosition);

        cartPosition = cartPositionService.save(cartPosition);
        update(user);

        return cartPosition;
    }

    public void removeFromCart(Integer userId, CartPosition cartPosition) {
        User user = find(userId);
        Cart cart = user.getCart();
        cartPosition = cartPositionService.find(cartPosition.getId());
        cart.getPositions()
                .remove(cartPosition);

        cartPositionService.delete(cartPosition.getId());
        update(user);
    }
}
