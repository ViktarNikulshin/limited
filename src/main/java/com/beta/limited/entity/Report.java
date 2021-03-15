package com.beta.limited.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String mobile;
    @Column
    private Double prise;
    @Column
    private String address;
    @Column
    private String phoneNumber;

}
