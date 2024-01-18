package com.beta.limited.controller.v2;


import com.beta.limited.mapper.UserMapper;
import com.beta.limited.model.UserDto;
import com.beta.limited.servise.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v2/user")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") Integer id) {
        return userMapper.domainToDto(userService.getUserById(id));
    }

    @GetMapping
    public List<UserDto> getAllUser() {
        return userMapper.domainListToDtoList(userService.getAllUser());
    }

    @PostMapping("/create")
    public void createUser(@RequestBody UserDto userDto) {
        userService.registerNewUserAccount(userDto);
    }

    @PostMapping("/update")
    public void updateUser(@RequestBody UserDto userDto){
        userService.updateUser(userDto);
    }

    @GetMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Integer id){
        userService.deleteUserById(id);
    }

    @GetMapping("/runners")
    public List<UserDto> geAllRunner() {
        return userMapper.domainListToDtoList(userService.getUsersByRoleRunner());
    }
}
