package com.beta.limited.servise.impl;

import com.beta.limited.entity.*;
import com.beta.limited.mapper.UserMapper;
import com.beta.limited.model.UserDto;
import com.beta.limited.repository.RoleRepository;
import com.beta.limited.repository.UserRepository;
import com.beta.limited.servise.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

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
        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findAllByLogin(login).orElse(null);
    }

    @Override
    public User getUserByTelegramName(String name) {
        return userRepository.findByTelegramName(name).orElse(null);
    }

    @Override
    public List<User> getUsersByRoleRunner() {
        Role role = roleRepository.getByName("ROLE_RUNNER");
        List<User> users =  userRepository.findAll().stream().filter(u -> u.getRoles().contains(role)).collect(Collectors.toList());
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
}
