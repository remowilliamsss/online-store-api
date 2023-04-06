package ru.egorov.onlinestoreapi.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.egorov.onlinestoreapi.model.Product;
import ru.egorov.onlinestoreapi.service.ProductService;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class ProductValidator implements Validator {
    private final ProductService productService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Product.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;
        int id = product.getId();

        try {
            productService.find(id);

        } catch (NoSuchElementException e) {
            errors.rejectValue("id", "",
                    String.format("product with id %d doesn't exist", id));
        }
    }
}
