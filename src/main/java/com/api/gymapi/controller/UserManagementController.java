package com.api.gymapi.controller;

import com.api.gymapi.Dtos.CustomerDto;
import com.api.gymapi.models.Customer;
import com.api.gymapi.models.Employee;
import com.api.gymapi.repository.CustomerRepository;
import com.api.gymapi.repository.EmployeeRepository;
import com.api.gymapi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public class UserManagementController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerService customerService;

    /**
     * Endpoint pour récupérer la liste des clients (seulement pour les utilisateurs connectés)
     */
    @GetMapping("/customers")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return ResponseEntity.ok(customers);
    }
    /**
     * Endpoint pour supprimer un client
     */

    @DeleteMapping("/customers/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteCustomer(@PathVariable long id) {
        try {
            customerRepository.deleteById(id);
            return ResponseEntity.ok("Client supprimé avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    /**
     * Endpoint pour modifier un client
     */
    @PutMapping("/customers/update/{id}")
    @PreAuthorize("isAuthenticated()") // Seuls les utilisateurs connectés peuvent modifier un client
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        try {
            Customer updatedCustomer = customerService.updateCustomer(id, customerDto);
            return ResponseEntity.ok(updatedCustomer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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
