package com.po.trucking.consignmentunit.repository;

import com.po.trucking.consignmentunit.model.ConsignmentUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsignmentUnitRepository extends JpaRepository<ConsignmentUnit, Long> {

    List<ConsignmentUnit> getAllByConsignmentId(Long consignmentId);

    long countDistinctByAmountAfter(int amount);
}

