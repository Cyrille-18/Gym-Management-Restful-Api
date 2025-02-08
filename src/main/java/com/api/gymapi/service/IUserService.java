package com.api.gymapi.service;

import com.api.gymapi.Dtos.CustomerDto;
import com.api.gymapi.Dtos.EmployeeDto;
import com.api.gymapi.models.Employee;
import com.api.gymapi.models.User;

public interface IUserService {
    public User registerUserForCustomer(CustomerDto customerDto);
    public boolean checkedPassword(String rawPassword, String encodedPassword);
    public Employee registerUserForEmployee(EmployeeDto employeeDto);
    public void updatePassword(User user, String newPassword);
//    public User getUserByUsername(String username);
//    public User createUser(User user);
//    public User updateUser(User user);
//    public User deleteUser(long id);
}
