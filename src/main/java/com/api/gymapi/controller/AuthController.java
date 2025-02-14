package com.api.gymapi.controller;

import com.api.gymapi.Dtos.*;
import com.api.gymapi.enums.Role;
import com.api.gymapi.models.Employee;
import com.api.gymapi.models.User;
import com.api.gymapi.repository.UserRepository;
import com.api.gymapi.service.JwtService;
import com.api.gymapi.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    /**
     * Endpoint pour l'enregistrement d'un client
     */

    @PostMapping("/register/customer")
    public CustomerResponseDto registerCustomer(@RequestBody CustomerDto customerDto) {
        return userService.registerUserForCustomer(customerDto);
    }


    /**
     * Endpoint pour l'enregistrement d'un employé
     */

    @PostMapping("/register/employee")
    public Employee registerEmployee(@RequestBody EmployeeDto employeeDTO) {
        if (employeeDTO.getRole() == null) {
            throw new IllegalArgumentException("Le rôle est obligatoire pour un employé.");
        }
        return userService.registerUserForEmployee(employeeDTO);
    }

    /**
     * Endpoint pour la connexion d'un utilisateur
     */

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        try {
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();

            User user = userService.findByUsername(username).orElse(null);

            if (user == null || !userService.checkedPassword(password, user.getPassword())) {
                return new LoginResponse(null, "Nom d'utilisateur ou mot de passe incorrect");
            }

            if (user.isFirstLogin() && user instanceof Employee employee) {
                if (employee.getRole() == Role.Receptionist || employee.getRole() == Role.Manager) {
                    return new LoginResponse(null, "Premier login - Veuillez changer votre mot de passe.");
                }
            }

            String token = (user instanceof Employee employee)
                    ? jwtService.generateToken(user.getUsername(), employee.getRole().name())
                    : jwtService.generateToken(user.getUsername(), null);

            return new LoginResponse(user, token);

        } catch (Exception e) {
            return new LoginResponse(null, "Erreur interne");
        }
    }

}
