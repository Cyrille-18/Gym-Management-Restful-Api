package com.api.gymapi.service;

import com.api.gymapi.Dtos.CustomerDto;
import com.api.gymapi.models.Customer;
import com.api.gymapi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer getCustomer(long id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, CustomerDto customerDto) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);

        if (existingCustomer.isEmpty()) {
            throw new RuntimeException("Client avec l'ID " + id + " introuvable.");
        }

        // Mise à jour des informations
        Customer customer = existingCustomer.get();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setRegistrationDate(customerDto.getRegistrationDate());

        return customerRepository.save(customer);
    }


    @Override
    public Customer getOneCustomer(long id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }
}
