package com.po.trucking.client.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientDto {

    private long id;
    private String name;
    private CarrierDto ownerOfClient;
    private String telephone;
    private double longitude;
    private double latitude;

}
