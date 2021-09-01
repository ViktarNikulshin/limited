package com.beta.limited.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Integer id;
    private String login;
    private String password;
    private String email;
    private String telegramName;
    private List<Role> roles;
}
