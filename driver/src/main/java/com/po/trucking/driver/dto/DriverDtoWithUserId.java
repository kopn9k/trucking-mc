package com.po.trucking.driver.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DriverDtoWithUserId {

    private long id;
    private String firstName;
    private String lastName;
    private String passportNumber;
    private UserEmailAndIdDto user;
    private Long userId;

}
