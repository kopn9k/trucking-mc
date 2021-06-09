package com.po.trucking.consignment.controller;

import com.po.trucking.consignment.dto.ConsignmentDto;
import com.po.trucking.consignment.service.ConsignmentService;
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
@RequestMapping("/api/v1/consignments/")
@CrossOrigin
public class ConsignmentRestControllerV1 {


    private final ConsignmentService consignmentService;

    @Autowired
    public ConsignmentRestControllerV1(ConsignmentService consignmentService) {
        this.consignmentService = consignmentService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ConsignmentDto> getConsignment(@PathVariable("id") long consignmentId) {
        Optional<ConsignmentDto> optionalConsignmentDto = this.consignmentService.getById(consignmentId);
        return optionalConsignmentDto
                .map(consignmentDto -> new ResponseEntity<>(consignmentDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping(value = "")
    public ResponseEntity<ConsignmentDto> createConsignment(@RequestBody @Valid ConsignmentDto consignmentDto) {

        ConsignmentDto savedConsignmentDto = this.consignmentService.create(consignmentDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedConsignmentDto.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "{id}")
    public ConsignmentDto updateClient(@PathVariable("id") long id, @RequestBody @Valid ConsignmentDto consignmentDto) {

        this.consignmentService.update(id, consignmentDto);
        return consignmentDto;
    }
}