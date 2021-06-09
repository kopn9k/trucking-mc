package com.po.trucking.driver.controller;

import com.po.trucking.driver.dto.DriverDto;
import com.po.trucking.driver.dto.DriverIdAndNameDto;
import com.po.trucking.driver.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/drivers/")
@CrossOrigin
public class DriverRestControllerV1 {


    private final DriverService driverService;

    @Autowired
    public DriverRestControllerV1(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<DriverDto> getDriver(@PathVariable("id") long driverId) {

        Optional<DriverDto> optionalDriverDto = this.driverService.getById(driverId);
        return optionalDriverDto
                .map(driverDto -> new ResponseEntity<>(driverDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping(value = "")
    public ResponseEntity<DriverDto> saveDriver(@RequestBody @Valid DriverDto driverDto) {

        DriverDto savedDriverDto = this.driverService.save(driverDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedDriverDto.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "{id}")
    public DriverDto updateDriver(@PathVariable("id") long id, @RequestBody @Valid DriverDto driverDto) {
        this.driverService.update(driverDto, id);
        return driverDto;
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<DriverDto> deleteDriver(@PathVariable("id") long id) {

        this.driverService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "")
    public Page<DriverDto> getAllDrivers(Pageable pageable) {
        return  this.driverService.getAll(pageable);
    }

    @GetMapping(value = "all")
    public List<DriverIdAndNameDto> getAllDrivers() {
        return  this.driverService.getAllDrivers();
    }

    @GetMapping(value = "name/{id}")
    public ResponseEntity<DriverIdAndNameDto> getDriverIdAndNameDto(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.driverService.getDriverIdAndNameDto(id), HttpStatus.OK);
    }
}
