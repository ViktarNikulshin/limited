package com.beta.limited.servise.impl;

import com.beta.limited.entity.*;
import com.beta.limited.mapper.UserMapper;
import com.beta.limited.model.AuthUserDto;
import com.beta.limited.model.UserDto;
import com.beta.limited.repository.RoleRepository;
import com.beta.limited.repository.UserRepository;
import com.beta.limited.servise.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void registerNewUserAccount(UserDto userDto) {
        User user = userMapper.dtoToDomain(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findAllByLogin(login)
                .orElse(null);
    }

    @Override
    public User getUserByTelegramName(String name) {
        return userRepository.findByTelegramName(name)
                .orElse(null);
    }

    @Override
    public List<User> getUsersByRoleRunner() {
        Role role = roleRepository.getByName("ROLE_RUNNER");
        List<User> users = new ArrayList<>();
        for (User u : userRepository.findAll()) {
            if (u.getRoles().contains(role)) {
                users.add(u);
            }
        }
        return users;
    }

    @Override
    public void updateUser(UserDto userDto) {
        User user = userRepository.findAllByLogin(userDto.getLogin())
                .orElseThrow(() -> new RuntimeException("Not found user : " + userDto.getLogin()));
        user.setLogin(userDto.getLogin());
        user.setEmail(userDto.getEmail());
        user.setTelegramName(userDto.getTelegramName());
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public AuthUserDto getAuthUser(UserDto userDto) {
        Optional<User> user = userRepository.findAllByLogin(userDto.getLogin());
        AuthUserDto authUserDto = new AuthUserDto();
        user.ifPresentOrElse(u -> {
            authUserDto.setId(u.getId());
            authUserDto.setEmail(u.getEmail());
            authUserDto.setToken("todo");
        }, user::orElseThrow);

        return authUserDto;
    }
}
