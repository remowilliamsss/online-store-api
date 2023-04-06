package ru.egorov.onlinestoreapi.mapper;

import org.mapstruct.Mapper;
import ru.egorov.onlinestoreapi.dto.ProductDto;
import ru.egorov.onlinestoreapi.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product product);

    Product toEntity(ProductDto productDto);
}
