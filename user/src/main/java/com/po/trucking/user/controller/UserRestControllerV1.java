package com.po.trucking.user.controller;

import com.po.trucking.user.dto.UserDto;
import com.po.trucking.user.dto.UserDtoWithPassword;
import com.po.trucking.user.dto.UserEmailAndIdDto;
import com.po.trucking.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users/")
@CrossOrigin
public class UserRestControllerV1 {

    private final UserService userService;

    @Autowired
    public UserRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDtoWithPassword> getUser(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable("id") long userID) {

        Optional<UserDtoWithPassword> optionalUserDtoWithPassword = this.userService.getById(userID, authorizationHeader);
        return optionalUserDtoWithPassword
                .map(userDtoWithPassword -> new ResponseEntity<>(userDtoWithPassword, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping(value = "")
    public ResponseEntity<UserDtoWithPassword> saveUser(@RequestBody @Valid UserDtoWithPassword userDtoWithPassword) {

        UserDtoWithPassword savedUserDto = this.userService.save(userDtoWithPassword);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUserDto.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "{id}")
    public UserDtoWithPassword updateUser(@PathVariable("id") long id, @RequestBody @Valid UserDtoWithPassword userDtoWithPassword, UriComponentsBuilder builder) {

        this.userService.update(userDtoWithPassword, id);

        return userDtoWithPassword;

    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<UserDtoWithPassword> deleteUser(@PathVariable("id") long id) {
        this.userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping(value = "")
    public Page<UserDto> getAllUsers(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorizationHeader, Pageable pageable) {
        return  this.userService.getAll(pageable, authorizationHeader);
    }

    @GetMapping(value = "all/{role}")
    public List<UserEmailAndIdDto> getAllUsersByRole(@PathVariable("role") String role) {
        return  this.userService.getAllByRole(role.toUpperCase());
    }

    @GetMapping(value = "{id}/email")
    public ResponseEntity<UserEmailAndIdDto> getUserEmailById(@PathVariable("id") Long id) {
        UserEmailAndIdDto userEmailAndIdDto = this.userService.getUserEmailById(id);
        return new ResponseEntity<>(userEmailAndIdDto, HttpStatus.OK);
    }


}