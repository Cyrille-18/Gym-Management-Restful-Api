package com.api.gymapi.service;

import com.api.gymapi.Dtos.CustomerDto;
import com.api.gymapi.Dtos.CustomerResponseDto;
import com.api.gymapi.Dtos.EmployeeDto;
import com.api.gymapi.models.Employee;
import com.api.gymapi.models.User;
import com.api.gymapi.models.Customer;
import com.api.gymapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public CustomerResponseDto registerUserForCustomer(CustomerDto customerDto) {
        // Générer un nom d'utilisateur unique
        String generatedUsername = generateUniqueUsername(customerDto.getFirstName(), customerDto.getLastName());

        // Générer un mot de passe aléatoire
        String plainPassword = generateRandomPassword(10);
        String hashedPassword = passwordEncoder.encode(plainPassword);

        // Créer le Customer
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setUsername(generatedUsername);
        customer.setPassword(hashedPassword);
        customer.setRegistrationDate(LocalDate.now());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setActiveSubscription(false);

        // Sauvegarder en base
        userRepository.save(customer);

        // Retourner les infos pour le front
        CustomerResponseDto responseDto = new CustomerResponseDto();
        responseDto.setUsername(generatedUsername);
        responseDto.setPassword(plainPassword); // Mot de passe en clair pour affichage

        return responseDto;
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

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void deleteUser(long id){
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("Utilisateur avec l'ID " + id + " introuvable.");
        }
    }
    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        return password.toString();
    }
    private String generateUniqueUsername(String firstName, String lastName) {
        String baseUsername = firstName.toLowerCase() + "_" + lastName.toLowerCase();
        int randomNumber = new SecureRandom().nextInt(900) + 100; // Nombre entre 100 et 999
        return baseUsername + randomNumber;
    }

}
