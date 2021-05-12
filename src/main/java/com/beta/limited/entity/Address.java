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
    @OneToOne
    @MapsId
    @JoinColumn(name = "report_id")
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

}
