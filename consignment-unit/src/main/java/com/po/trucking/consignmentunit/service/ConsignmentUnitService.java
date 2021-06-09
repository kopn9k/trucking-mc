package com.po.trucking.consignmentunit.service;

import com.po.trucking.consignmentunit.dto.ConsignmentUnitDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ConsignmentUnitService {

    Optional<ConsignmentUnitDto> getById(long id);

    ConsignmentUnitDto create(ConsignmentUnitDto consignmentUnitDto);

    void update(long id, ConsignmentUnitDto consignmentUnitDto);

    void delete(long id);

    List<ConsignmentUnitDto> getAll();
}
