package com.po.trucking.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthenticationUserDto {

    private String email;
    private String password;
}
