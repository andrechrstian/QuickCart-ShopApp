package org.example.tokonyadia.service;

import org.example.tokonyadia.dto.request.CustomerRequest;
import org.example.tokonyadia.dto.response.CustomerResponse;
import org.example.tokonyadia.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    CustomerResponse saveCustomer (CustomerRequest request);
    List<CustomerResponse> getAllCustomer ();
    CustomerResponse getCustomerById (String id);
    void deleteCustomer (String id);
    CustomerResponse updateCustomer (CustomerRequest request);
    Customer getById(String id);

    Page<CustomerResponse> getCustomerPerPage(Pageable pageable, CustomerRequest customerRequest);
}
