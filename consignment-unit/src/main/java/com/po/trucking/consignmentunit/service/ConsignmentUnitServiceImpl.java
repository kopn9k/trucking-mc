package com.po.trucking.consignmentunit.service;

import com.po.trucking.consignmentunit.dto.ConsignmentUnitDto;
import com.po.trucking.consignmentunit.model.ConsignmentUnit;
import com.po.trucking.consignmentunit.repository.ConsignmentUnitRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ConsignmentUnitServiceImpl implements ConsignmentUnitService {

    private final ModelMapper modelMapper;
    private final ConsignmentUnitRepository consignmentUnitRepository;

    @Autowired
    public ConsignmentUnitServiceImpl(ConsignmentUnitRepository consignmentUnitRepository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.consignmentUnitRepository = consignmentUnitRepository;
    }

    @Override
    public Optional<ConsignmentUnitDto> getById(long id) {
        log.info("In ConsignmentUnitServiceImpl getById {}", id);
        Optional<ConsignmentUnit> optionalConsignmentUnit = consignmentUnitRepository.findById(id);
        return optionalConsignmentUnit.map(consignmentUnit -> modelMapper.map(consignmentUnit, ConsignmentUnitDto.class));
    }

    @Override
    public ConsignmentUnitDto create(ConsignmentUnitDto consignmentUnitDto) {
        log.info("In ConsignmentUnitServiceImpl create {}", consignmentUnitDto);
        ConsignmentUnit consignmentUnit = modelMapper.map(consignmentUnitDto, ConsignmentUnit.class);
        ConsignmentUnit savedConsignmentUnit = consignmentUnitRepository.save(consignmentUnit);
        return modelMapper.map(savedConsignmentUnit, ConsignmentUnitDto.class);
    }

    @Override
    public void update(long id, ConsignmentUnitDto consignmentUnitDto) {
        log.info("In ConsignmentUnitServiceImpl update {}", consignmentUnitDto);
        consignmentUnitDto.setId(id);
        ConsignmentUnit consignmentUnit = modelMapper.map(consignmentUnitDto, ConsignmentUnit.class);
        consignmentUnitRepository.save(consignmentUnit);
    }

    @Override
    public void delete(long id) {
        log.info("In ConsignmentUnitServiceImpl delete {}", id);
        consignmentUnitRepository.deleteById(id);
    }

    @Override
    public List<ConsignmentUnitDto> getAll() {
        log.info("In ConsignmentUnitServiceImpl getAll");
        List<ConsignmentUnit> consignmentUnits = consignmentUnitRepository.findAll();
        return consignmentUnits.stream().map(consignmentUnit -> modelMapper
                .map(consignmentUnit, ConsignmentUnitDto.class)).collect(Collectors.toList());
    }

}