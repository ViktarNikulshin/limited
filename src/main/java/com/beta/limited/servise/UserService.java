package com.beta.limited.servise;

import com.beta.limited.entity.User;
import com.beta.limited.entity.UserDto;

import javax.jws.soap.SOAPBinding;
import java.util.List;

public interface UserService {

    User registerNewUserAccount(UserDto user);

    User getUserByLogin(String login);

    User getUserByTelegramName(String name);

    List<User> getUsersByRoleRunner();
}
