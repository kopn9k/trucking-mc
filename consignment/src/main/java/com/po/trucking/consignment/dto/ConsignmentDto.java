package com.po.trucking.consignment.dto;

import com.po.trucking.consignment.model.ConsignmentStatus;
import com.po.trucking.consignment.model.TransportationType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConsignmentDto {

    private long id;
    private TransportationType transportationType;
    private ConsignmentStatus consignmentStatus;
}
