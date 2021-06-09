package com.po.trucking.consignment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "consignments")
public class Consignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transportation_type")
    @Enumerated
    private TransportationType transportationType;

    @Column(name = "consignment_status")
    @Enumerated
    private ConsignmentStatus consignmentStatus;


}
