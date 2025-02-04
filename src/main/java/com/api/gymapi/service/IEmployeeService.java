package com.api.gymapi.service;

import com.api.gymapi.models.Employee;

import java.util.List;

public interface IEmployeeService {
    public List<Employee> getAllEmployees();
    public Employee getEmployee(long id);
    public Employee addEmployee(Employee employee);
    public Employee updateEmployee(Employee employee);
    public void deleteEmployee(long id);
}
