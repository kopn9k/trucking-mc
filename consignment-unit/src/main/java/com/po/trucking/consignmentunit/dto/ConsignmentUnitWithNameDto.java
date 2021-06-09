package com.po.trucking.consignmentunit.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConsignmentUnitWithNameDto {

    private long id;
    private int amount;
    private String productName;
}