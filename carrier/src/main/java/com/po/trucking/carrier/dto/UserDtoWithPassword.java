package com.po.trucking.carrier.dto;

import com.po.trucking.carrier.model.Role;
import lombok.*;

import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDtoWithPassword {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Calendar birthday;
    private CarrierDto company;
    private String addressCity;
    private String addressStreet;
    private String addressHome;
    private String addressFlat;
    private Role role;

}