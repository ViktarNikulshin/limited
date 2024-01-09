package com.beta.limited.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String login;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private String telegramName;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")}
    ,inverseJoinColumns = {@JoinColumn( name = "ROLE_ID", referencedColumnName = "ID")})
    @ToString.Exclude
    private List<Role> roles;
    @OneToMany(mappedBy = "runner")
    @ToString.Exclude
    private List<Report> reports;
}
