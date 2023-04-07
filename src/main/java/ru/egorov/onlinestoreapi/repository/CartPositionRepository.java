package ru.egorov.onlinestoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.egorov.onlinestoreapi.model.CartPosition;


@Repository
public interface CartPositionRepository extends JpaRepository<CartPosition, Integer> {
}
