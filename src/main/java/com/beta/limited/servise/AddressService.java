package com.beta.limited.servise;

import com.beta.limited.entity.Address;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.util.List;


public interface AddressService {

    Address findAddressToReport(String s, Address address) throws Exception;
    void buildRouting(List<Address> addresses) throws IOException;
    void findOptimalRouting(List<Address> addresses, Integer id);
}
