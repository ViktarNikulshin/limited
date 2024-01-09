package com.beta.limited.servise;

import com.beta.limited.entity.User;
import com.beta.limited.model.UserDto;

import java.util.List;

public interface UserService {

    List<User> getAllUser();

    void registerNewUserAccount(UserDto user);

    User getUserByLogin(String login);

    User getUserByTelegramName(String name);

    List<User> getUsersByRoleRunner();
}
