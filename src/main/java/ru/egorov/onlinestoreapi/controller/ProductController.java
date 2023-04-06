package ru.egorov.onlinestoreapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.egorov.onlinestoreapi.dto.ProductDto;
import ru.egorov.onlinestoreapi.exception.ProductNotAddedOrUpdatedException;
import ru.egorov.onlinestoreapi.mapper.ProductMapper;
import ru.egorov.onlinestoreapi.model.Product;
import ru.egorov.onlinestoreapi.service.ProductService;
import ru.egorov.onlinestoreapi.validator.ProductValidator;

import java.util.NoSuchElementException;

import static ru.egorov.onlinestoreapi.util.ErrorMessageBuilder.getErrorMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ProductValidator productValidator;

    @PostMapping("/add")
    public ResponseEntity<ProductDto> create(@RequestBody @Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ProductNotAddedOrUpdatedException(getErrorMessage(bindingResult));
        }

        product = productService.create(product);

        return new ResponseEntity<>(productMapper.toDto(product), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable("id") Integer id,
                                             @RequestBody @Valid Product product, BindingResult bindingResult) {

        product.setId(id);
        productValidator.validate(product, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ProductNotAddedOrUpdatedException(getErrorMessage(bindingResult));
        }

        product = productService.update(product);

        return new ResponseEntity<>(productMapper.toDto(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatusCode> delete(@PathVariable("id") Integer id) {
        try {
            productService.find(id);
            productService.delete(id);

        } catch (NoSuchElementException e) {
            throw new ProductNotAddedOrUpdatedException("Product with this id doesn't exist!");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
