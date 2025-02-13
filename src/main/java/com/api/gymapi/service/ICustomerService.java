package com.api.gymapi.service;

import com.api.gymapi.Dtos.CustomerDto;
import com.api.gymapi.models.Customer;

import java.util.List;

public interface ICustomerService {
    public Customer getCustomer(long id);
    public List<Customer> getCustomers();
    public Customer saveCustomer(Customer customer);
    public Customer updateCustomer(Long id, CustomerDto customerDto);
    public void deleteCustomer(Customer customer);
    public Customer getOneCustomer(long id);
}
