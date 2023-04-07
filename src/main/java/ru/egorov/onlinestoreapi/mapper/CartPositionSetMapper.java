package ru.egorov.onlinestoreapi.mapper;

import org.mapstruct.Mapper;
import ru.egorov.onlinestoreapi.dto.CartPositionDto;
import ru.egorov.onlinestoreapi.model.CartPosition;

import java.util.Set;

@Mapper(componentModel = "spring", uses = CartPositionMapper.class)
public interface CartPositionSetMapper {

    Set<CartPositionDto> toDtoSet(Set<CartPosition> cartPositions);
}
