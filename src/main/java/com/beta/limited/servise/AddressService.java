package com.beta.limited.servise;

import com.beta.limited.entity.Address;

import java.util.List;


public interface AddressService {

    Address findAddressToReport(String s, Address address) throws Exception;

    void findOptimalRouting(List<Address> addresses, Integer id);
}
