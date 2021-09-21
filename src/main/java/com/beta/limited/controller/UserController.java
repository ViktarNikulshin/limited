package com.beta.limited.controller;

import com.beta.limited.entity.User;
import com.beta.limited.model.UserDto;
import com.beta.limited.servise.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public User createUser(@RequestBody UserDto userDto){
        return userService.registerNewUserAccount(userDto);
    }
}
