package org.example.tokonyadia.controller;

import lombok.AllArgsConstructor;
import org.example.tokonyadia.constant.APIUrl;
import org.example.tokonyadia.dto.request.CustomerRequest;
import org.example.tokonyadia.dto.response.CustomerResponse;
import org.example.tokonyadia.entity.Customer;
import org.example.tokonyadia.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = APIUrl.CUSTOMER_API)
public class CustomerController {

    private CustomerService customerService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest request) {
        CustomerResponse createdCustomer = customerService.saveCustomer(request);
        return ResponseEntity.ok(createdCustomer);
    }

    @GetMapping
    public List<Customer> getCustomer(@RequestParam(name = "name", required = false) String name){
        return customerService.getAllCustomer(name);
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable String id) {
        return customerService.getCustomerById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer (@PathVariable String id) {
        customerService.deleteCustomer(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable String id,@RequestBody Customer customer) {
        try {
            customer.setId(id);
            Customer updateCustomer = customerService.updateCustomer(customer);
            return ResponseEntity.ok(updateCustomer);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
