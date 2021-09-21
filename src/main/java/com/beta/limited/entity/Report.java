package com.beta.limited.entity;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

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
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deliveryDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "runner")
    private User runner;
    @OneToOne(mappedBy = "report", cascade = CascadeType.ALL)
    private Address address;
}
