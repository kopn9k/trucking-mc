package com.po.trucking.driver.service;

import com.po.trucking.driver.data.UsersServiceClient;
import com.po.trucking.driver.dto.DriverDto;
import com.po.trucking.driver.dto.DriverDtoWithUserId;
import com.po.trucking.driver.dto.DriverIdAndNameDto;
import com.po.trucking.driver.dto.UserEmailAndIdDto;
import com.po.trucking.driver.model.Driver;
import com.po.trucking.driver.repository.DriverRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
public class DriverServiceImpl implements DriverService {

    private final ModelMapper modelMapper;
    private final DriverRepository driverRepository;
    private final UsersServiceClient usersServiceClient;

    @Autowired
    public DriverServiceImpl(DriverRepository driverRepository, ModelMapper modelMapper, UsersServiceClient usersServiceClient) {
        this.modelMapper = modelMapper;
        this.driverRepository = driverRepository;
        this.usersServiceClient = usersServiceClient;
    }

    @Override
    @Transactional
    public Optional<DriverDto> getById(long id) {
        log.info("In DriverServiceImpl getById {}", id);
        Optional<Driver> optionalDriver = driverRepository.findById(id);
        Driver driver = optionalDriver.orElseGet(Driver::new);
        DriverDto driverDto = modelMapper.map(driver, DriverDto.class);
        UserEmailAndIdDto userEmailAndIdDto = getUserEmailAndIdDto(driver.getUserId());
        driverDto.setUser(userEmailAndIdDto);
        return Optional.of(driverDto);
    }

    @Override
    @Transactional
    public DriverDto save(DriverDto driverDto) {
        log.info("In DriverServiceImpl save {}", driverDto);
        Driver driver = modelMapper.map(driverDto, Driver.class);
        driver.setUserId(driverDto.getUser().getId());
        Driver savedDriver = driverRepository.save(driver);
        return modelMapper.map(savedDriver, DriverDto.class);
    }

    @Override
    @Transactional
    public void update(DriverDto driverDto, long id) {
        log.info("In DriverServiceImpl update {}", driverDto);
        driverDto.setId(id);
        Driver driver = modelMapper.map(driverDto, Driver.class);
        driver.setUserId(driverDto.getUser().getId());
        driverRepository.save(driver);
    }

    @Override
    @Transactional
    public void delete(long id) {
        log.info("In DriverServiceImpl delete {}", id);
        driverRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Page<DriverDto> getAll(Pageable pageable) {
        log.info("In DriverServiceImpl getAll");
        Page<Driver> drivers = driverRepository.findAll(pageable);
        Page<DriverDtoWithUserId> driverDtoWithUserIds = drivers.map(driver -> modelMapper.map(driver, DriverDtoWithUserId.class));
        driverDtoWithUserIds.forEach(driverDto -> {
            UserEmailAndIdDto userEmailAndIdDto = getUserEmailAndIdDto(driverDto.getUserId());
            driverDto.setUser(userEmailAndIdDto);
        });
        return driverDtoWithUserIds.map(driverDtoWithUserId -> modelMapper.map(driverDtoWithUserId, DriverDto.class));
    }

    @Override
    @Transactional
    public List<DriverIdAndNameDto> getAllDrivers() {
        log.info("In DriverServiceImpl getAllDrivers");
        List<Driver> drivers = driverRepository.findAll();
        return drivers.stream().map(driver -> modelMapper.map(driver, DriverIdAndNameDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DriverIdAndNameDto getDriverIdAndNameDto(Long id) {
        log.info("In DriverServiceImpl getDriverIdAndNameDto {}", id);
        Optional<Driver> optionalDriver = driverRepository.findById(id);
        Driver driver = optionalDriver.orElseGet(Driver::new);
        return modelMapper.map(driver, DriverIdAndNameDto.class);
    }

    private UserEmailAndIdDto getUserEmailAndIdDto(Long id) {
        ResponseEntity<UserEmailAndIdDto> userEmailAndIdDtoResponseEntity = usersServiceClient.getUserEmailById(id);
        return userEmailAndIdDtoResponseEntity.getBody();
    }
}
