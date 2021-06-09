package com.po.trucking.user.dto;

import com.po.trucking.user.model.Role;
import lombok.*;

import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Calendar birthday;
    private CarrierDto company;
    private String addressCity;
    private String addressStreet;
    private String addressHome;
    private String addressFlat;
    private Role role;
}