package com.po.trucking.driver.repository;


import com.po.trucking.driver.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    List<Driver> findAll();
    Optional<Driver> findByUserId (Long userId);
}