package com.beta.limited.servise.impl;

import com.beta.limited.entity.Reference;
import com.beta.limited.repository.ReferenceRepository;
import com.beta.limited.servise.ReferenceService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReferenceServiceImpl implements ReferenceService {
    private final ReferenceRepository referenceRepository;
    @Override
    public List<Reference> getAllReferenceByType(Integer type) {
        return referenceRepository.findAllByType(type);
    }
}
