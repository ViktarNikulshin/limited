package com.beta.limited.model;

import com.beta.limited.entity.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Integer id;
    private String login;
    private String password;
    private String email;
    private String telegramName;
    private String name;
    private List<Role> roles;
}
