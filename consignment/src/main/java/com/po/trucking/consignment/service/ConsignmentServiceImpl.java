package com.po.trucking.consignment.service;

import com.po.trucking.consignment.dto.ConsignmentDto;
import com.po.trucking.consignment.model.Consignment;
import com.po.trucking.consignment.repository.ConsignmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ConsignmentServiceImpl implements ConsignmentService {


    private final ModelMapper modelMapper;
    private final ConsignmentRepository consignmentRepository;

    @Autowired
    public ConsignmentServiceImpl(ConsignmentRepository consignmentRepository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.consignmentRepository = consignmentRepository;
    }

    @Override
    public Optional<ConsignmentDto> getById(long id) {
        log.info("In ConsignmentServiceImpl getById {}", id);
        Optional<Consignment> optionalConsignment = consignmentRepository.findById(id);
        return optionalConsignment.map(consignment -> modelMapper.map(consignment, ConsignmentDto.class));
    }

    @Override
    public ConsignmentDto create(ConsignmentDto consignmentDto) {
        log.info("In ConsignmentServiceImpl create {}", consignmentDto);
        Consignment consignment = modelMapper.map(consignmentDto, Consignment.class);
        Consignment savedConsignment = consignmentRepository.save(consignment);
        return modelMapper.map(savedConsignment, ConsignmentDto.class);
    }

    @Override
    public void update(long id, ConsignmentDto consignmentDto) {
        log.info("In ConsignmentServiceImpl update {}", consignmentDto);
        consignmentDto.setId(id);
        Consignment consignment = modelMapper.map(consignmentDto, Consignment.class);
        consignmentRepository.save(consignment);
    }

}
