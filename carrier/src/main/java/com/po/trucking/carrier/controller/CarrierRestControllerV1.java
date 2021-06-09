package com.po.trucking.carrier.controller;

import com.po.trucking.carrier.dto.CarrierDto;
import com.po.trucking.carrier.dto.UserDtoWithPassword;
import com.po.trucking.carrier.service.CarrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/carriers/")
@CrossOrigin
public class CarrierRestControllerV1 {

    private final CarrierService carrierService;

    @Autowired
    public CarrierRestControllerV1(CarrierService carrierService) {
        this.carrierService = carrierService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<CarrierDto> getCarrier(@PathVariable("id") long carrierId) {
        Optional<CarrierDto> optionalCarrierDto = this.carrierService.getById(carrierId);
        return optionalCarrierDto
                .map(carrierDto -> new ResponseEntity<>(carrierDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping(value = "")
    public ResponseEntity<CarrierDto> saveCarrier(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                                  @RequestBody @Valid UserDtoWithPassword userDtoWithPassword) {

        CarrierDto savedCarrierDto = this.carrierService.add(authorizationHeader, userDtoWithPassword);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedCarrierDto.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "{id}")
    public CarrierDto updateCarrier(@PathVariable("id") long id, @RequestBody @Valid CarrierDto carrierDto) {

        this.carrierService.update(carrierDto, id);
        return carrierDto;
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<CarrierDto> deleteCarrier(@PathVariable("id") long id) {

        this.carrierService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "")
    public Page<CarrierDto> getAllCarriers(Pageable pageable) {
        return  this.carrierService.getAll(pageable);
    }
}
