package ru.egorov.onlinestoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.egorov.onlinestoreapi.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
}
