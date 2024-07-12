package org.example.tokonyadia.service.impl;

import lombok.AllArgsConstructor;
import org.example.tokonyadia.dto.request.CustomerRequest;
import org.example.tokonyadia.dto.response.CustomerResponse;
import org.example.tokonyadia.entity.Customer;
import org.example.tokonyadia.repository.CustomerRepository;
import org.example.tokonyadia.service.CustomerService;
import org.example.tokonyadia.utils.specifications.CustomerSpesification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Override
    public CustomerResponse saveCustomer(CustomerRequest request) {
        Customer customer = customerRepository.saveAndFlush(
                Customer.builder()
                        .id(request.getId())
                        .name(request.getName())
                        .birthDate(request.getBirthDate())
                        .address(request.getAddress())
                        .phone(request.getPhone())
                        .user(request.getUser())
                        .build()
        );
        return convertToCustomerResponse(customer);
    }


    @Override
    public List<CustomerResponse> getAllCustomer() {
        return customerRepository.findAll().stream()
                .map(this::convertToCustomerResponse)
                .toList();
    }

    @Override
    public CustomerResponse getCustomerById(String id) {
        Customer customer = findByIdOrThrowNotFound(id);
        return convertToCustomerResponse(customer);
    }

    @Override
    public void deleteCustomer(String id) {
        findByIdOrThrowNotFound(id);
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerResponse updateCustomer(CustomerRequest request) {
        findByIdOrThrowNotFound(request.getId());
        Customer customer = customerRepository.saveAndFlush(
                Customer.builder()
                        .id(request.getId())
                        .name(request.getName())
                        .birthDate(request.getBirthDate())
                        .address(request.getAddress())
                        .phone(request.getPhone())
                        .build()
        );
        return convertToCustomerResponse(customer);
    }

    @Override
    public Customer getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    private Customer findByIdOrThrowNotFound(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Omeprazole"));
    }

    private CustomerResponse convertToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phone(customer.getPhone())
                .birthDate(customer.getBirthDate())
                .address(customer.getAddress())
                .build();
    }

    @Override
    public Page<CustomerResponse> getCustomerPerPage(Pageable pageable,CustomerRequest customerRequest) {

        Specification<Customer> specification = CustomerSpesification.getSpesification(customerRequest);
        Page <Customer> customerList = customerRepository.findAll(specification,pageable);

        return customerList.map(this::convertToCustomerResponse);
    }
}
