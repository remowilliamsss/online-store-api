package ru.egorov.onlinestoreapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.egorov.onlinestoreapi.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findAllByCategoryIgnoreCase(String category, Pageable pageable);

    Page<Product> findAllBySubcategoryIgnoreCase(String subcategory, Pageable pageable);

    Page<Product> findAllByNameContainingIgnoreCase(String subcategory, Pageable pageable);
}
