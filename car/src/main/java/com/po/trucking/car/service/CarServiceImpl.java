package com.po.trucking.car.service;

import com.po.trucking.car.data.CarriersServiceClient;
import com.po.trucking.car.data.DriversServiceClient;
import com.po.trucking.car.dto.CarDto;
import com.po.trucking.car.dto.CarrierDto;
import com.po.trucking.car.dto.DriverIdAndNameDto;
import com.po.trucking.car.model.Car;
import com.po.trucking.car.model.CarStatus;
import com.po.trucking.car.model.TransportationType;
import com.po.trucking.car.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CarServiceImpl implements CarService {

    private final ModelMapper modelMapper;
    private final CarRepository carRepository;
    private final CarriersServiceClient carriersServiceClient;
    private final DriversServiceClient driversServiceClient;


    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, CarriersServiceClient carriersServiceClient, DriversServiceClient driversServiceClient) {
        this.modelMapper = modelMapper;
        this.carRepository = carRepository;
        this.carriersServiceClient = carriersServiceClient;
        this.driversServiceClient = driversServiceClient;
    }

    @Override
    public Optional<CarDto> getById(long id) {
        log.info("In CarServiceImpl getById {}", id);
        Optional<Car> optionalCar = carRepository.findByIdAndCarrierId(id, this.getCarrierId());
        Car car = optionalCar.orElseGet(Car::new);
        CarrierDto carrierDto = getCarrierDtoById(car.getCarrierId());
        DriverIdAndNameDto driverIdAndNameDto = getDriverIdAndNameDto(car.getDriverId());
        CarDto carDto = modelMapper.map(car, CarDto.class);
        carDto.setCarrier(carrierDto);
        carDto.setDriver(driverIdAndNameDto);
        return Optional.of(carDto);
    }

    @Override
    public CarDto create(CarDto carDto) {
        log.info("In CarServiceImpl create {}", carDto);
        Car car = modelMapper.map(carDto, Car.class);
        car.setCarrierId(this.getCarrierId());
        car.setDriverId(carDto.getDriver().getId());
        Car savedCar = carRepository.save(car);
        return modelMapper.map(savedCar, CarDto.class);
    }

    @Override
    public void update(long id, CarDto carDto) {
        log.info("In CarServiceImpl update {}", carDto);
        carDto.setId(id);
        Car car = modelMapper.map(carDto, Car.class);
        car.setCarrierId(this.getCarrierId());
        car.setDriverId(carDto.getDriver().getId());
        carRepository.save(car);
    }

    @Override
    public void delete(long id) {
        log.info("In CarServiceImpl delete {}", id);
        carRepository.deleteByIdAndCarrierId(id, this.getCarrierId());
    }

    @Override
    public Page<CarDto> getAll(Pageable pageable) {
        log.info("In CarServiceImpl getAll");
        Page<Car> cars = carRepository.findAllByCarrierId(pageable, this.getCarrierId());
        return cars.map(car -> {
             CarDto carDto = modelMapper.map(car, CarDto.class);
             carDto.setCarrier(getCarrierDtoById(car.getCarrierId()));
             carDto.setDriver(getDriverIdAndNameDto(car.getDriverId()));
             return carDto;
        });
    }

    @Override
    public List<CarDto> getAll() {
        log.info("In CarServiceImpl getAll");
        List<Car> cars = carRepository.findAllByCarrierId(this.getCarrierId());
        return cars.stream().map(car -> {
            CarDto carDto = modelMapper.map(car, CarDto.class);
            carDto.setCarrier(getCarrierDtoById(car.getCarrierId()));
            carDto.setDriver(getDriverIdAndNameDto(car.getDriverId()));
            return carDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<CarDto> getAllFreeCarsByTransportationType(String transportationType) {
        log.info("In getAllFreeCarsByTransportationType getAll");
        List<Car> cars = carRepository.findAllByCarrierIdAndTransportationTypeAndCarStatus(this.getCarrierId(), TransportationType.valueOf(transportationType), CarStatus.FREE);
        return cars.stream().map(car -> modelMapper.map(car, CarDto.class)).collect(Collectors.toList());
    }

    private Long getCarrierId() {
        Long carrierId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return carrierId;
    }

    private CarrierDto getCarrierDtoById(Long carrierId) {
        ResponseEntity<CarrierDto> carrierResponseEntity = carriersServiceClient.getCarrier(carrierId);
        return carrierResponseEntity.getBody();
    }

    private DriverIdAndNameDto getDriverIdAndNameDto(Long driverId) {
        ResponseEntity<DriverIdAndNameDto> driverIdAndNameDtoResponseEntity = driversServiceClient.getDriverIdAndNameDto(driverId);
        return driverIdAndNameDtoResponseEntity.getBody();
    }
}
