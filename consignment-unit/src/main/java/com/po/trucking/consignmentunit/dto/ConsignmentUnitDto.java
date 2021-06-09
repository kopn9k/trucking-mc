package com.po.trucking.consignmentunit.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConsignmentUnitDto {

    private long id;
    private int amount;
    private long productId;
}
