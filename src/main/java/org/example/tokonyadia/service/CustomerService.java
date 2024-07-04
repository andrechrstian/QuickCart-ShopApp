package org.example.tokonyadia.service;

import org.example.tokonyadia.dto.request.CustomerRequest;
import org.example.tokonyadia.dto.response.CustomerResponse;
import org.example.tokonyadia.entity.Customer;

import java.util.List;

public interface CustomerService {
    CustomerResponse saveCustomer (CustomerRequest request);
    List<Customer> getAllCustomer (String name);
    Customer getCustomerById (String id);
    void deleteCustomer (String id);
    Customer updateCustomer (Customer update);


}
