package com.beta.limited.model;


import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date deliveryDate;
    private UserDto runner;
    private AddressDto address;

}
