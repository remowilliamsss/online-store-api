package ru.egorov.onlinestoreapi.mapper;

import org.mapstruct.Mapper;
import ru.egorov.onlinestoreapi.dto.UserDto;
import ru.egorov.onlinestoreapi.model.User;

@Mapper(componentModel = "spring", uses = {ProductSetMapper.class})
public interface UserMapper {

    UserDto toDto(User user);
}
