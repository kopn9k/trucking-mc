package com.po.trucking.client.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarrierDto {

    private Long id;
    private String name;
    private String telephone;
    private boolean isActive;


}
