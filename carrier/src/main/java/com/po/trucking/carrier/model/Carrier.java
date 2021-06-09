package com.po.trucking.carrier.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "carriers")
public class Carrier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String telephone;

    @Column(name = "is_active")
    private boolean isActive;

    public Carrier(String name, String telephone, boolean isActive) {
        this.name = name;
        this.telephone = telephone;
        this.isActive = isActive;
    }

}
