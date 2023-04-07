package ru.egorov.onlinestoreapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.egorov.onlinestoreapi.model.CartPosition;
import ru.egorov.onlinestoreapi.repository.CartPositionRepository;

@Service
@RequiredArgsConstructor
public class CartPositionService {
    private final CartPositionRepository cartPositionRepository;

    public CartPosition save(CartPosition cartPosition) {
        return cartPositionRepository.save(cartPosition);
    }

    public CartPosition find(Integer id) {
        return cartPositionRepository.findById(id)
                .orElseThrow();
    }

    public void delete(Integer id) {
        cartPositionRepository.deleteById(id);
    }
}
