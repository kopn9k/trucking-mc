package com.po.trucking.carrier.service;

import com.po.trucking.carrier.dto.CarrierDto;
import com.po.trucking.carrier.dto.UserDtoWithPassword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CarrierService {

    Optional<CarrierDto> getById(long id);

    CarrierDto add(String authorizationHeader ,UserDtoWithPassword userDto);

    void update(CarrierDto carrierDto, long id);

    void delete(long id);

    Page<CarrierDto> getAll(Pageable pageable);
}
