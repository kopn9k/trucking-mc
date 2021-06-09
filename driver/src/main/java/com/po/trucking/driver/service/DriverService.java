package com.po.trucking.driver.service;

import com.po.trucking.driver.dto.DriverDto;
import com.po.trucking.driver.dto.DriverIdAndNameDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DriverService {

    Optional<DriverDto> getById(long id);

    DriverDto save(DriverDto driverDto);

    void update(DriverDto driverDto, long id);

    void delete(long id);

    Page<DriverDto> getAll(Pageable pageable);

    List<DriverIdAndNameDto> getAllDrivers();

    DriverIdAndNameDto getDriverIdAndNameDto(Long id);


}
