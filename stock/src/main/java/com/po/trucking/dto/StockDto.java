package com.po.trucking.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StockDto {

    private long id;
    private String name;
    private CarrierDto ownerOfStock;
    private String telephone;
    private double longitude;
    private double latitude;

}
