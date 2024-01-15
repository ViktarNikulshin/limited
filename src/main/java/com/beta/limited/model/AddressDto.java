package com.beta.limited.model;


import lombok.Data;

@Data
public class AddressDto {
    private Integer id;
    private String city;
    private String street;
    private Integer house;
    private String building;
    private Integer flat;
    private Integer entrance;
    private Integer floor;
    private String pos;
    private String lat;
    private String lon;
    private String fullAddress;
    private Integer routing;

}
