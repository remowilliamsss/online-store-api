package ru.egorov.onlinestoreapi.mapper;

import org.mapstruct.Mapper;
import ru.egorov.onlinestoreapi.dto.CartPositionDto;
import ru.egorov.onlinestoreapi.model.CartPosition;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface CartPositionMapper {

    CartPositionDto toDto(CartPosition cartPosition);

    CartPosition toEntity(CartPositionDto cartPositionDto);
}
