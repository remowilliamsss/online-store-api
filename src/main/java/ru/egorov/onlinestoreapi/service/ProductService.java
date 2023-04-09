package ru.egorov.onlinestoreapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.egorov.onlinestoreapi.model.Product;
import ru.egorov.onlinestoreapi.model.CategoryType;
import ru.egorov.onlinestoreapi.model.SubcategoryType;
import ru.egorov.onlinestoreapi.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product find(Integer id) {
        return productRepository.findById(id)
                .orElseThrow();
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> findAllByCategory(CategoryType category, Pageable pageable) {
        return switch (category) {
            case candles -> productRepository.findAllByCandleIsTrue(pageable);
            case diffs -> productRepository.findAllByDiffuserIsTrue(pageable);
            case autodiffs -> productRepository.findAllByAutodiffuserIsTrue(pageable);
        };
    }

    public Page<Product> findAllBySubcategory(SubcategoryType sub, Pageable pageable) {
        return switch (sub) {
            case news -> productRepository.findAllByIsNewIsTrue(pageable);
            case hits -> productRepository.findAllByIsHitIsTrue(pageable);
            case sales -> productRepository.findAllByIsSaleIsTrue(pageable);
        };
    }

    public Page<Product> findAllByName(String name, Pageable pageable) {
        return productRepository.findAllByNameContainingIgnoreCase(name, pageable);
    }

    @Transactional
    public void delete(Integer id) {
        productRepository.deleteById(id);
    }
}
