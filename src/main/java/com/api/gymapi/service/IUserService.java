package com.api.gymapi.service;

import com.api.gymapi.Dtos.CustomerDto;
import com.api.gymapi.Dtos.CustomerResponseDto;
import com.api.gymapi.Dtos.EmployeeDto;
import com.api.gymapi.models.Employee;
import com.api.gymapi.models.User;

import java.util.Optional;

public interface IUserService {
    public CustomerResponseDto registerUserForCustomer(CustomerDto customerDto);
    public boolean checkedPassword(String rawPassword, String encodedPassword);
    public Employee registerUserForEmployee(EmployeeDto employeeDto);
    public void updatePassword(User user, String newPassword);
    public Optional<User> findByUsername(String username);
   public void deleteUser(long id);

}
