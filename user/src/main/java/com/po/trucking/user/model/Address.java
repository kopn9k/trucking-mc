package com.po.trucking.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class Address {

    @Column(name="address_city")
    private String addressCity;

    @Column(name="address_street")
    private String addressStreet;

    @Column(name="address_home")
    private String addressHome;

    @Column(name="address_flat")
    private String addressFlat;

}
