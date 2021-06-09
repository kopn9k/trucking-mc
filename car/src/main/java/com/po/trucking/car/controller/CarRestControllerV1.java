package com.po.trucking.car.controller;


import com.po.trucking.car.dto.CarDto;
import com.po.trucking.car.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cars/")
@CrossOrigin
public class CarRestControllerV1 {


    private final CarService carService;

    @Autowired
    public CarRestControllerV1(CarService carService) {
        this.carService = carService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<CarDto> getCar(@PathVariable("id") long carId) {

        Optional<CarDto> optionalCarDto = this.carService.getById(carId);
        return optionalCarDto
                .map(carDto -> new ResponseEntity<>(carDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping(value = "")
    public ResponseEntity<CarDto> createCar(@RequestBody @Valid CarDto carDto) {

        CarDto savedCarDto = this.carService.create(carDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedCarDto.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "{id}")
    public CarDto updateCar(@PathVariable("id") long id, @RequestBody @Valid CarDto carDto) {

        this.carService.update(id, carDto);

        return carDto;

    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<CarDto> deleteCar(@PathVariable("id") long id) {
        this.carService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping(value = "")
    public Page<CarDto> getAllCars(Pageable pageable) {
        return  this.carService.getAll(pageable);
    }

    @GetMapping(value = "all")
    public List<CarDto> getAllCars() {
        return  this.carService.getAll();
    }

    @GetMapping(value = "all/{transportationType}")
    public List<CarDto> getAllFreeCarsByTransportationType(@PathVariable("transportationType") String transportationType) {
        return  this.carService.getAllFreeCarsByTransportationType(transportationType.toUpperCase());
    }
}
