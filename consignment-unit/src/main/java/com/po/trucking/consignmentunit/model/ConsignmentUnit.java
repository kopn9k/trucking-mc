package com.po.trucking.consignmentunit.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "consignment_units")
public class ConsignmentUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "consignment_id")
    private Long consignmentId;

    private int amount;

    @Column(name = "product_id")
    private Long productId;
}
