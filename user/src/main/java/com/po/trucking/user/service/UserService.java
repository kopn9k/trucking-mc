package com.po.trucking.user.service;


import com.po.trucking.user.dto.UserDto;
import com.po.trucking.user.dto.UserDtoWithPassword;
import com.po.trucking.user.dto.UserEmailAndIdDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<UserDtoWithPassword> getById(long id, String authorizationHeader);

    UserDtoWithPassword save(UserDtoWithPassword userDto);

    void update(UserDtoWithPassword userDto, long id);

    void delete(long id);

    Page<UserDto> getAll(Pageable pageable, String authorizationHeader);

    List<UserEmailAndIdDto> getAllByRole(String role);

    UserDtoWithPassword getUserByEmail(String email, String authorizationHeader);

    UserEmailAndIdDto getUserEmailById(Long id);
}
