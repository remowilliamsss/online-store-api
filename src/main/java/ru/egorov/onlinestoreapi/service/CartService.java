package ru.egorov.onlinestoreapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.egorov.onlinestoreapi.model.Cart;
import ru.egorov.onlinestoreapi.repository.CartRepository;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }
}
