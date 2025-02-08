package com.api.gymapi.service;

import com.api.gymapi.Dtos.CustomerDto;
import com.api.gymapi.Dtos.EmployeeDto;
import com.api.gymapi.models.Employee;
import com.api.gymapi.models.User;
import com.api.gymapi.models.Customer;
import com.api.gymapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User registerUserForCustomer(CustomerDto customerDto) {
        // Création d'un utilisateur de type Client
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        customer.setRegistrationDate(LocalDateTime.now());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setActiveSubscription(false);
        // Client n'a pas de rôle
        return userRepository.save(customer);
    }

    @Override
    public Employee registerUserForEmployee(EmployeeDto employeeDto) {
        // Création d'un utilisateur de type Employé
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setUsername(employeeDto.getUsername());
        employee.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
        employee.setRole(employeeDto.getRole());

        // Contrôle si le rôle de l'employé est "Admin", et si c'est le cas, mettre firstLogin à false
        if ("Admin".equalsIgnoreCase(String.valueOf(employeeDto.getRole()))) {
            employee.setFirstlogin(false);
        } else {
            employee.setFirstlogin(true); // Sinon, on peut mettre firstLogin à true pour les autres rôles
        }

        return userRepository.save(employee);
    }


    @Override
    public boolean checkedPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
