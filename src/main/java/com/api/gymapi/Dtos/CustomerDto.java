package com.api.gymapi.Dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerDto {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private LocalDateTime registrationDate;
    private String  phoneNumber;
}
