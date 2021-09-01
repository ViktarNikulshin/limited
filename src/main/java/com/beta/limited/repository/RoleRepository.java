package com.beta.limited.repository;

import com.beta.limited.entity.Role;
import com.beta.limited.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role getByName(String name);
}
