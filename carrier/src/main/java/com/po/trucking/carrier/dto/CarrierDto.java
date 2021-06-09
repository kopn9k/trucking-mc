package com.po.trucking.carrier.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarrierDto {

    private long id;
    private String name;
    private String telephone;
    private boolean isActive;


}