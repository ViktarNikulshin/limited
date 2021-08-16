package com.beta.limited.servise;

import com.beta.limited.entity.Reference;

import java.util.List;

public interface ReferenceService {
    List<Reference> getAllReferenceByType(Integer type);
}
