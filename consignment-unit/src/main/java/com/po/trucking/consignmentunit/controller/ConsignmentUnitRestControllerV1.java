package com.po.trucking.consignmentunit.controller;

import com.po.trucking.consignmentunit.dto.ConsignmentUnitDto;
import com.po.trucking.consignmentunit.service.ConsignmentUnitService;
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
@RequestMapping("/api/v1/consignmentUnits/")
@CrossOrigin
public class ConsignmentUnitRestControllerV1 {

    private final ConsignmentUnitService consignmentUnitService;

    @Autowired
    public ConsignmentUnitRestControllerV1(ConsignmentUnitService consignmentUnitService) {
        this.consignmentUnitService = consignmentUnitService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ConsignmentUnitDto> getConsignmentUnit(@PathVariable("id") long consignmentUnitId) {
        Optional<ConsignmentUnitDto> optionalConsignmentUnitDto = this.consignmentUnitService.getById(consignmentUnitId);
        return optionalConsignmentUnitDto
                .map(consignmentUnitDto -> new ResponseEntity<>(consignmentUnitDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping(value = "")
    public ResponseEntity<ConsignmentUnitDto> createConsignmentUnit(@RequestBody @Valid ConsignmentUnitDto consignmentUnitDto) {

        ConsignmentUnitDto savedConsignmentUnitDto = this.consignmentUnitService.create(consignmentUnitDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedConsignmentUnitDto.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "{id}")
    public ConsignmentUnitDto updateConsignmentUnit(@PathVariable("id") long id, @RequestBody @Valid ConsignmentUnitDto consignmentUnitDto) {

        this.consignmentUnitService.update(id, consignmentUnitDto);
        return consignmentUnitDto;
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ConsignmentUnitDto> deleteConsignmentUnit(@PathVariable("id") long id) {

        this.consignmentUnitService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(value = "all")
    public List<ConsignmentUnitDto> getAllConsignmentUnits() {
        return  this.consignmentUnitService.getAll();
    }
}
