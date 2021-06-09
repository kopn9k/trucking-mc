package com.po.trucking.carrier.repository;

import com.po.trucking.carrier.model.Carrier;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrierRepository extends PagingAndSortingRepository<Carrier, Long> {
}
