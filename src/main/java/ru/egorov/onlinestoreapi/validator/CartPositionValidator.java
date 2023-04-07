package ru.egorov.onlinestoreapi.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.egorov.onlinestoreapi.model.CartPosition;
import ru.egorov.onlinestoreapi.model.Product;
import ru.egorov.onlinestoreapi.service.ProductService;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class CartPositionValidator implements Validator {
    private final ProductService productService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(CartPosition.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CartPosition cartPosition = (CartPosition) target;
        Product product = cartPosition.getProduct();

        if (product == null) {
            return;
        }

        int productId = cartPosition.getProduct()
                .getId();
        Integer amount = cartPosition.getAmount();

        try {
            product = productService.find(productId);

            if (amount != null && amount > product.getAmount()) {
                errors.rejectValue("amount", "", "there aren't such amount of products");
            }

        } catch (NoSuchElementException e) {
            errors.rejectValue("product", "",
                    String.format("product with id %d doesn't exist", productId));
        }
    }
}
