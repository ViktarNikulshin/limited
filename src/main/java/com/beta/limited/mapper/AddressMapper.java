package com.beta.limited.mapper;


import com.beta.limited.entity.Address;
import com.beta.limited.model.AddressDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
        Address dtoToDomain (AddressDto addressDto);
        AddressDto domainToDto (Address Address);
        List<AddressDto> domainListToDtoList (List<Address> Addresses);
}
