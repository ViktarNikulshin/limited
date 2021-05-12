package com.beta.limited.servise.impl;

import com.beta.limited.entity.Address;
import com.beta.limited.repository.AddressRepository;
import com.beta.limited.servise.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository repository;
    @Override
    public Address createAddress(Address address) {
        return repository.save(address);

    }
}
