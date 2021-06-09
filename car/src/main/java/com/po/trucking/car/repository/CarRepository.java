package com.po.trucking.car.repository;

import com.po.trucking.car.model.Car;
import com.po.trucking.car.model.CarStatus;
import com.po.trucking.car.model.TransportationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Page<Car> findAllByCarrierId(Pageable pageable, Long carrierId);
    List<Car> findAllByCarrierId(Long carrierId);
    Optional<Car> findByIdAndCarrierId(long id, Long carrierId);
    void deleteByIdAndCarrierId(long id, Long carrierId);
    Optional<Car> findByDriverId(Long driverId);
    List<Car> findAllByCarrierIdAndTransportationTypeAndCarStatus(Long carrierId, TransportationType transportationType, CarStatus carStatus);


}