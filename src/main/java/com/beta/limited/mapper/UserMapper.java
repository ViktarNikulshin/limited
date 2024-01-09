package com.beta.limited.mapper;


import com.beta.limited.entity.User;
import com.beta.limited.model.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
        User dtoToDomain (UserDto userDto);
        UserDto domainToDto (User user);
        List<UserDto> domainListToDtoList (List<User> users);
}
