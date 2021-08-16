package com.beta.limited.servise;

import com.beta.limited.entity.Address;


public interface AddressService {

    Address findAddressToReport(String s, Address address) throws Exception;
}
