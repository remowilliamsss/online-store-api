package ru.egorov.onlinestoreapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.egorov.onlinestoreapi.dto.ProductDto;
import ru.egorov.onlinestoreapi.exception.ProductNotAddedOrUpdatedException;
import ru.egorov.onlinestoreapi.exception.ProductNotFoundException;
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

    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody @Valid ProductDto productDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ProductNotAddedOrUpdatedException(getErrorMessage(bindingResult));
        }

        Product product = productMapper.toEntity(productDto);
        product.setId(0);

        product = productService.save(product);

        return new ResponseEntity<>(productMapper.toDto(product), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> find(@PathVariable("id") Integer id) {
        try {
            Product product = productService.find(id);

            return new ResponseEntity<>(productMapper.toDto(product), HttpStatus.OK);

        } catch (NoSuchElementException e) {
            throw new ProductNotFoundException(String.format("Product with id %d doesn't exist!", id));
        }
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> findAll(
            @PageableDefault(size = Integer.MAX_VALUE, page = 0) Pageable pageable) {

        Page<Product> products = productService.findAll(pageable);

        return new ResponseEntity<>(products.map(productMapper::toDto), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDto>> search(@RequestParam("q") String query,
            @PageableDefault(size = Integer.MAX_VALUE, page = 0) Pageable pageable) {

        Page<Product> products = productService.findAllByName(query, pageable);

        return new ResponseEntity<>(products.map(productMapper::toDto), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable("id") Integer id,
                                             @RequestBody @Valid ProductDto productDto, BindingResult bindingResult) {
        Product product = productMapper.toEntity(productDto);
        product.setId(id);
        productValidator.validate(product, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ProductNotAddedOrUpdatedException(getErrorMessage(bindingResult));
        }

        product = productService.save(product);

        return new ResponseEntity<>(productMapper.toDto(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatusCode> delete(@PathVariable("id") Integer id) {
        try {
            productService.find(id);
            productService.delete(id);

        } catch (NoSuchElementException e) {
            throw new ProductNotAddedOrUpdatedException(String.format("Product with id %d doesn't exist!", id));
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
