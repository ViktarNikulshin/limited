package com.beta.limited.entity;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
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
    @Column(name = "order_text")
    private String order;
    @Column
    private String phoneNumber;
    @Column
    private Boolean executed;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "runner")
    private User runner;
    @OneToOne(mappedBy = "report", cascade = CascadeType.ALL)
    private Address address;
}
