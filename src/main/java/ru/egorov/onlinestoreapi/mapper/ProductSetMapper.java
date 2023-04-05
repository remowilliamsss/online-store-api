package ru.egorov.onlinestoreapi.mapper;

import org.mapstruct.Mapper;
import ru.egorov.onlinestoreapi.dto.ProductDto;
import ru.egorov.onlinestoreapi.model.Product;

import java.util.Set;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface ProductSetMapper {

    Set<ProductDto> toDtoSet(Set<Product> products);
}
