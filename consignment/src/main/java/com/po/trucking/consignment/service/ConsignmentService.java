package com.po.trucking.consignment.service;

import com.po.trucking.consignment.dto.ConsignmentDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ConsignmentService {


    Optional<ConsignmentDto> getById(long id);

    ConsignmentDto create(ConsignmentDto consignmentDto);

    void update(long id, ConsignmentDto consignmentDto);

}