package com.po.trucking.car.dto;

import com.po.trucking.car.model.CarStatus;
import com.po.trucking.car.model.TransportationType;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarDto {

    private long id;
    private String carNumber;
    private CarrierDto carrier;
    private double fuelConsumption;
    private DriverIdAndNameDto driver;
    private int capacity;
    private TransportationType transportationType;
    private CarStatus carStatus;
}