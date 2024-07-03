package org.example.tokonyadia.service;

import org.example.tokonyadia.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer saveCustomer (Customer customer);
    List<Customer> getAllCustomer ();
    Customer deleteCustomer (int id);
    Customer updateCustomer (Customer updateCustomer);
}
