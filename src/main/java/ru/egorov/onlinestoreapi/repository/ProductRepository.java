package ru.egorov.onlinestoreapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.egorov.onlinestoreapi.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findAllByNameContainingIgnoreCase(String subcategory, Pageable pageable);

    Page<Product> findAllByCandleIsTrue(Pageable pageable);

    Page<Product> findAllByDiffuserIsTrue(Pageable pageable);

    Page<Product> findAllByAutodiffuserIsTrue(Pageable pageable);

    Page<Product> findAllByIsNewIsTrue(Pageable pageable);

    Page<Product> findAllByIsHitIsTrue(Pageable pageable);

    Page<Product> findAllByIsSaleIsTrue(Pageable pageable);
}
