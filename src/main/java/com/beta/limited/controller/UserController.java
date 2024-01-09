package com.beta.limited.controller;

import com.beta.limited.entity.User;
import com.beta.limited.mapper.UserMapper;
import com.beta.limited.model.UserDto;
import com.beta.limited.servise.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/users")
    public String getAllUsers( Model model){
        List<UserDto> userList = userMapper.domainListToDtoList(userService.getAllUser());
        model.addAttribute("users", userList);
        return "users";
    }
    @PostMapping("/user")
    public String createUser(@RequestBody UserDto userDto, Model model){

        userService.registerNewUserAccount(userDto);
        model.addAttribute("user", userDto);
        return "user";
    }
}
