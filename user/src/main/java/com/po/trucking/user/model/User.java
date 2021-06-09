package com.po.trucking.user.model;

import com.po.trucking.user.converter.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Temporal(value = TemporalType.DATE)
    private Calendar birthday;

    @Column(name = "carrier_id")
    private Long carrierId;

    private Address address;

    @Enumerated
    private Role role;


    public User(String email, String password, String firstName, String lastName, Calendar birthday, Address address, Role role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.role = role;
        this.address = address;
    }
}
