package com.beta.limited.controller.v2;


import com.beta.limited.model.AuthUserDto;
import com.beta.limited.model.UserDto;
import com.beta.limited.servise.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/user/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public AuthUserDto loginUser(@RequestBody UserDto userDto) {

        return userService.getAuthUser(userDto);
    }

}
