package com.beta.limited.servise;

import com.beta.limited.entity.User;
import com.beta.limited.model.AuthUserDto;
import com.beta.limited.model.UserDto;

import java.util.List;

public interface UserService {

    List<User> getAllUser();

    User getUserById(Integer id);

    void registerNewUserAccount(UserDto user);

    User getUserByLogin(String login);

    User getUserByTelegramName(String name);

    List<User> getUsersByRoleRunner();

    void updateUser(UserDto userDto);

    void deleteUserById(Integer id);

    AuthUserDto getAuthUser(UserDto userDto);
}
