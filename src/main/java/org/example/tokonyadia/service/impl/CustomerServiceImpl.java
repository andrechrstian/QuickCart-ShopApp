package org.example.tokonyadia.service.impl;

import lombok.AllArgsConstructor;
import org.example.tokonyadia.dto.request.CustomerRequest;
import org.example.tokonyadia.dto.response.CustomerResponse;
import org.example.tokonyadia.entity.Customer;
import org.example.tokonyadia.repository.CustomerRepository;
import org.example.tokonyadia.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Override
    public CustomerResponse saveCustomer(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        customer.setBirthDate(request.getBirthDate());

        customer = customerRepository.saveAndFlush(customer);

        CustomerResponse response = new CustomerResponse();
        response.setName(customer.getName());
        response.setPhoneNumber((customer.getPhone()));
        response.setAddress(customer.getAddress());

        return response;
    }
    

    @Override
    public List<Customer> getAllCustomer(String name) {
        if (name != null) {
            return customerRepository.findAllByNameLike(name);
        }
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElse(null);
    }

    @Override
    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Customer updateCustomer(Customer update) {
        if (!customerRepository.existsById((update.getId()))) {
            throw new IllegalArgumentException("Customer ID " + update.getId() + " does not exist");
        }
        return customerRepository.saveAndFlush(update);
    }
}
