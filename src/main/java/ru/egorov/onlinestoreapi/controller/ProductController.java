package ru.egorov.onlinestoreapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
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
import ru.egorov.onlinestoreapi.model.CategoryType;
import ru.egorov.onlinestoreapi.model.SubcategoryType;
import ru.egorov.onlinestoreapi.service.ProductService;
import ru.egorov.onlinestoreapi.validator.ProductValidator;

import java.util.NoSuchElementException;

import static ru.egorov.onlinestoreapi.util.ErrorMessageBuilder.getErrorMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/products")
@Tag(name = "Товары", description = "Методы для работы с товарами")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ProductValidator productValidator;

    @PostMapping
    @Operation(summary = "Создание нового товара")
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
    @Operation(summary = "Информация о товаре по его id")
    public ResponseEntity<ProductDto> find(
            @PathVariable("id") @Parameter(description = "Идентификатор товара") Integer id) {

        try {
            Product product = productService.find(id);

            return new ResponseEntity<>(productMapper.toDto(product), HttpStatus.OK);

        } catch (NoSuchElementException e) {
            throw new ProductNotFoundException(String.format("Product with id %d doesn't exist!", id));
        }
    }

    @GetMapping
    @Operation(summary = "Получение всех товаров")
    public ResponseEntity<Page<ProductDto>> findAll(
            @PageableDefault(size = Integer.MAX_VALUE, page = 0) @ParameterObject Pageable pageable) {

        Page<Product> products = productService.findAll(pageable);

        return new ResponseEntity<>(products.map(productMapper::toDto), HttpStatus.OK);
    }

    @GetMapping("/search")
    @Operation(summary = "Поиск по товарам")
    public ResponseEntity<Page<ProductDto>> search(
            @RequestParam("q") @Parameter(description = "Поисковой запрос") String query,
            @PageableDefault(size = Integer.MAX_VALUE, page = 0) @ParameterObject Pageable pageable) {

        Page<Product> products = productService.findAllByName(query, pageable);

        return new ResponseEntity<>(products.map(productMapper::toDto), HttpStatus.OK);
    }

    @GetMapping("/categories/{category}")
    @Operation(summary = "Получение всех товаров категории")
    public ResponseEntity<Page<ProductDto>> findByCategory(
            @PathVariable("category") @Parameter(description = "Обозначение категории") CategoryType category,
                            @PageableDefault(size = Integer.MAX_VALUE, page = 0) @ParameterObject Pageable pageable) {

        Page<Product> products = productService.findAllByCategory(category, pageable);

        return new ResponseEntity<>(products.map(productMapper::toDto), HttpStatus.OK);
    }

    @GetMapping("/subs/{sub}")
    @Operation(summary = "Получение всех товаров подкатегории")
    public ResponseEntity<Page<ProductDto>> findBySubcategory(
            @PathVariable("sub") @Parameter(description = "Обозначение подкатегории") SubcategoryType sub,
                           @PageableDefault(size = Integer.MAX_VALUE, page = 0) @ParameterObject Pageable pageable) {

        Page<Product> products = productService.findAllBySubcategory(sub, pageable);

        return new ResponseEntity<>(products.map(productMapper::toDto), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Обновление информации о товаре")
    public ResponseEntity<ProductDto> update(
            @PathVariable("id") @Parameter(description = "Идентификатор товара") Integer id,
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
    @Operation(summary = "Удаление товара по id")
    public ResponseEntity<HttpStatusCode> delete(
            @PathVariable("id") @Parameter(description = "Идентификатор товара") Integer id) {

        try {
            productService.find(id);
            productService.delete(id);

        } catch (NoSuchElementException e) {
            throw new ProductNotAddedOrUpdatedException(String.format("Product with id %d doesn't exist!", id));
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
