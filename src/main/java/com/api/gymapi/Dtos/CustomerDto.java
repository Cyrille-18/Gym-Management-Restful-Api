package com.api.gymapi.Dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerDto {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private LocalDate registrationDate;
    private String  phoneNumber;
}
