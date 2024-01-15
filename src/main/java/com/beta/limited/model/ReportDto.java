package com.beta.limited.model;


import lombok.Data;

import java.util.Date;

@Data
public class ReportDto {
    private Integer id;
    private String mobile;
    private Double prise;
    private String order;
    private String phoneNumber;
    private Boolean executed;
    private Date deliveryDate;
    private UserDto runner;
    private AddressDto address;

}
