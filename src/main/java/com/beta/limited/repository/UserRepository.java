package com.beta.limited.repository;

import com.beta.limited.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findAllByLogin(String login);
    Optional<User> findByTelegramName(String name);
    List<User> findAll();
}
