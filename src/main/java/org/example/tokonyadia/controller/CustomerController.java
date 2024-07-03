package org.example.tokonyadia.controller;

import org.example.tokonyadia.entity.Customer;
import org.example.tokonyadia.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Customer postCustomer (@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @GetMapping
    public List<Customer> getCustomer(){
        return customerService.getAllCustomer();
    }

    @PutMapping
    public Customer putCustomer(@RequestBody Customer customer) {
        return customerService.updateCustomer(customer);
    }

    @DeleteMapping
    public Customer deleteCustomer (@RequestBody int id) {
        return customerService.deleteCustomer(id);
    }
}
