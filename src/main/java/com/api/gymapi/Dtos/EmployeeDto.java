package com.api.gymapi.Dtos;
import com.api.gymapi.enums.Role;
import lombok.Data;

@Data
public class EmployeeDto {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private  boolean firstlogin;
    private Role role;
}
