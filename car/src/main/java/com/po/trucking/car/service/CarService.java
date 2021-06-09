package com.po.trucking.car.service;
import com.po.trucking.car.dto.CarDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CarService {

    Optional<CarDto> getById(long id);

    CarDto create(CarDto carDto);

    void update(long id, CarDto carDto);

    void delete(long id);

    Page<CarDto> getAll(Pageable pageable);

    List<CarDto> getAll();

    List<CarDto> getAllFreeCarsByTransportationType(String transportationType);
}
