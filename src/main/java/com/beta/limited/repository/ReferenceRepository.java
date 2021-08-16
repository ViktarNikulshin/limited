package com.beta.limited.repository;

import com.beta.limited.entity.Reference;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReferenceRepository extends CrudRepository<Reference, Integer> {
    List<Reference> findAllByType(Integer type);
}
