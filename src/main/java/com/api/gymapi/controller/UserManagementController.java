package com.api.gymapi.controller;

import com.api.gymapi.models.Customer;
import com.api.gymapi.models.Employee;
import com.api.gymapi.repository.CustomerRepository;
import com.api.gymapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usermanagement")
public class UserManagementController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Endpoint pour récupérer la liste des clients
     */
    @GetMapping("/clients")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return ResponseEntity.ok(customers);
    }

    /**
     * Endpoint pour récupérer la liste des employés
     */
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return ResponseEntity.ok(employees);
    }
}
