package ru.egorov.onlinestoreapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.egorov.onlinestoreapi.model.Product;
import ru.egorov.onlinestoreapi.model.User;
import ru.egorov.onlinestoreapi.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ProductService productService;

    public User save(User user) {
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

        user.getFavorites()
                .add(product);
        product.getLikes()
                .add(user);

        save(user);
        product = productService.save(product);

        return product;
    }

    public void removeFavorite(Integer userId, Product product) {
        User user = find(userId);
        product = productService.find(product.getId());

        user.getFavorites()
                .remove(product);
        product.getLikes()
                .remove(user);

        save(user);
        productService.save(product);
    }
}
