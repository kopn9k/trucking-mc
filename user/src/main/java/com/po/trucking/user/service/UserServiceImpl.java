package com.po.trucking.user.service;

import com.po.trucking.user.data.CarriersServiceClient;
import com.po.trucking.user.dto.CarrierDto;
import com.po.trucking.user.dto.UserDto;
import com.po.trucking.user.dto.UserDtoWithPassword;
import com.po.trucking.user.dto.UserEmailAndIdDto;
import com.po.trucking.user.model.Carrier;
import com.po.trucking.user.model.Role;
import com.po.trucking.user.model.User;
import com.po.trucking.user.repository.UserRepository;
import com.po.trucking.user.security.JwtUser;
import com.po.trucking.user.security.JwtUserFactory;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CarriersServiceClient carriersServiceClient;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, CarriersServiceClient carriersServiceClient) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.carriersServiceClient = carriersServiceClient;
    }

    @Override
    @Transactional
    public Optional<UserDtoWithPassword> getById(long id, String authorizationHeader) {
        log.info("In UserServiceImpl getById {}", id);
        Optional<User> optionalUser = userRepository.findByIdAndCarrierId(id, this.getCarrierId());
        User user = optionalUser.orElseGet(User::new);
        UserDtoWithPassword userDtoWithPassword = modelMapper.map(user, UserDtoWithPassword.class);
        CarrierDto carrierDto = getCarrierDtoById(authorizationHeader, this.getCarrierId());
        userDtoWithPassword.setCompany(carrierDto);
        return Optional.of(userDtoWithPassword);
    }

    @Override
    @Transactional
    public UserDtoWithPassword save(UserDtoWithPassword userDtoWithPassword) {
        log.info("In UserServiceImpl save {}", userDtoWithPassword);
        User user = modelMapper.map(userDtoWithPassword, User.class);
        String password = user.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encodedPassword);
        user.setCarrierId(this.getCarrierId());
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDtoWithPassword.class);

    }

    @Override
    @Transactional
    public void update(UserDtoWithPassword userDtoWithPassword, long id) {
        log.info("In UserServiceImpl update {}", userDtoWithPassword);
        userDtoWithPassword.setId(id);
        User user = modelMapper.map(userDtoWithPassword, User.class);
        user.setCarrierId(this.getCarrierId());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(long id) {
        log.info("In UserServiceImpl delete {}", id);
        userRepository.deleteByIdAndCarrierId(id, this.getCarrierId());
    }

    @Override
    @Transactional
    public Page<UserDto> getAll(Pageable pageable, String authorizationHeader) {
        log.info("In UserServiceImpl getAll");
        Long carrierId = this.getCarrierId();
        Page<User> users = userRepository.findAllByCarrierId(pageable, carrierId);
        Page<UserDto> userDtos = users.map(user -> modelMapper.map(user, UserDto.class));
        userDtos.forEach(userDto -> {
            CarrierDto carrierDto = getCarrierDtoById(authorizationHeader, carrierId);
            userDto.setCompany(carrierDto);
        });
        return userDtos;
    }

    @Override
    @Transactional
    public List<UserEmailAndIdDto> getAllByRole(String role) {
        log.info("In UserServiceImpl getAllByRole");
        Long carrierId = this.getCarrierId();
        Role roleEnumElement = Role.valueOf(role);
        List<User> users = userRepository.findAllByRole(roleEnumElement);
        return users.stream().map(user -> modelMapper.map(user, UserEmailAndIdDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDtoWithPassword getUserByEmail(String email, String authorizationHeader) {
        log.info("In UserServiceImpl getUserByEmail {}", email);
        User user = userRepository.findByEmail(email);
        Long carrierId = user.getCarrierId();
        UserDtoWithPassword userDtoWithPassword = modelMapper.map(user, UserDtoWithPassword.class);
        CarrierDto carrierDto = getCarrierDtoById(authorizationHeader, carrierId);
        userDtoWithPassword.setCompany(carrierDto);
        return userDtoWithPassword;
    }

    @Override
    @Transactional
    public UserEmailAndIdDto getUserEmailById(Long id) {
        log.info("In UserServiceImpl getUserEmailById {}", id);
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseGet(User::new);
        return modelMapper.map(user, UserEmailAndIdDto.class);
    }

    private Long getCarrierId() {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwtUser.getCarrierId();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN loadUserByUsername - user with username: {} successfully loaded", email);

        return jwtUser;
    }

    private CarrierDto getCarrierDtoById(String authorizationHeader, Long carrierId) {
        ResponseEntity<CarrierDto> carrierResponseEntity = carriersServiceClient.getCarrier(authorizationHeader, carrierId);
        return carrierResponseEntity.getBody();
    }
}
