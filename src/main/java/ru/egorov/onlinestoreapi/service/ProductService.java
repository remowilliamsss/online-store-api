package ru.egorov.onlinestoreapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.egorov.onlinestoreapi.model.Product;
import ru.egorov.onlinestoreapi.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product find(Integer id) {
        return productRepository.findById(id)
                .orElseThrow();
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> findAllByCategory(String category, Pageable pageable) {
        return productRepository.findAllByCategoryIgnoreCase(category, pageable);
    }

    public Page<Product> findAllBySubcategory(String subcategory, Pageable pageable) {
        return productRepository.findAllBySubcategoryIgnoreCase(subcategory, pageable);
    }

    public Page<Product> findAllByName(String name, Pageable pageable) {
        return productRepository.findAllByNameContainingIgnoreCase(name, pageable);
    }

    public Product update(Product product) {

        return productRepository.save(product);
    }

    public void delete(Integer id) {
        productRepository.deleteById(id);
    }
}
