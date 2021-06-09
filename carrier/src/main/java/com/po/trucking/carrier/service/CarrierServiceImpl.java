package com.po.trucking.carrier.service;

import com.po.trucking.carrier.data.UsersServiceClient;
import com.po.trucking.carrier.dto.CarrierDto;
import com.po.trucking.carrier.dto.UserDtoWithPassword;
import com.po.trucking.carrier.model.Carrier;
import com.po.trucking.carrier.repository.CarrierRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class CarrierServiceImpl implements CarrierService {

    private final ModelMapper modelMapper;
    private final CarrierRepository carrierRepository;
    private final UsersServiceClient usersServiceClient;

    @Autowired
    public CarrierServiceImpl(ModelMapper modelMapper, CarrierRepository carrierRepository, UsersServiceClient usersServiceClient) {
        this.modelMapper = modelMapper;
        this.carrierRepository = carrierRepository;
        this.usersServiceClient = usersServiceClient;
    }

    @Override
    @Transactional
    public Optional<CarrierDto> getById(long id) {
        log.info("In CarrierServiceImpl getById {}", id);
        Optional<Carrier> optionalCarrier = carrierRepository.findById(id);
        return optionalCarrier.map(carrier -> modelMapper.map(carrier, CarrierDto.class));
    }

    @Override
    @Transactional
    public CarrierDto add(String authorizationHeader, UserDtoWithPassword userDto) {
        CarrierDto carrierDto = userDto.getCompany();
        log.info("In CarrierServiceImpl add {} {}", carrierDto, userDto);
        Carrier carrier = modelMapper.map(carrierDto, Carrier.class);
        Carrier savedCarrier = carrierRepository.save(carrier);
        CarrierDto savedCarrierDto = modelMapper.map(savedCarrier, CarrierDto.class);
        userDto.setCompany(savedCarrierDto);
        usersServiceClient.saveUser(authorizationHeader, userDto);
        return savedCarrierDto;
    }

    @Override
    @Transactional
    public void update(CarrierDto carrierDto, long id) {
        log.info("In CarrierServiceImpl update {}", carrierDto);
        carrierDto.setId(id);
        Carrier carrier = modelMapper.map(carrierDto, Carrier.class);
        carrierRepository.save(carrier);
    }

    @Override
    @Transactional
    public void delete(long id) {
        log.info("In CarrierServiceImpl delete {}", id);
        carrierRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Page<CarrierDto> getAll(Pageable pageable) {
        log.info("In CarrierServiceImpl getAll");
        Page<Carrier> carriers = carrierRepository.findAll(pageable);
        return carriers.map(carrier -> modelMapper.map(carrier, CarrierDto.class));
    }
}
