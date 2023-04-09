package ru.egorov.onlinestoreapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.egorov.onlinestoreapi.model.CartPosition;
import ru.egorov.onlinestoreapi.repository.CartPositionRepository;

@Service
@RequiredArgsConstructor
public class CartPositionService {
    private final CartPositionRepository cartPositionRepository;

    @Transactional
    public CartPosition save(CartPosition cartPosition) {
        return cartPositionRepository.save(cartPosition);
    }

    public CartPosition find(Integer id) {
        return cartPositionRepository.findById(id)
                .orElseThrow();
    }

    @Transactional
    public void delete(Integer id) {
        cartPositionRepository.deleteById(id);
    }
}
