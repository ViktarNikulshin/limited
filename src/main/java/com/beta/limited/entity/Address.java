package com.beta.limited.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "address")
public class Address {
    @Id
    @Column(name = "report_id")
    private Integer id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    @MapsId
    private Report report;
    @Column
    private String city;
    @Column
    private String street;
    @Column
    private Integer house;
    @Column
    private String building;
    @Column
    private Integer flat;
    @Column
    private Integer entrance;
    @Column
    private Integer floor;
    @Column
    private String pos;
    @Column
    private String lat;
    @Column
    private String lon;
    @Column
    private String fullAddress;
    @Column
    private Integer routing;

}
