package com.po.trucking.car.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "cars")
public class Car{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "car_number")
    private String carNumber;

    @Column(name = "carrier_id")
    private Long carrierId;

    @Column(name = "fuel_consumption")
    private double fuelConsumption;

    @Column(name = "driver_id")
    private Long driverId;

    private int capacity;

    @Column(name = "transportation_type")
    @Enumerated
    private TransportationType transportationType;

    @Column(name = "car_status")
    @Enumerated
    private CarStatus carStatus;
}
