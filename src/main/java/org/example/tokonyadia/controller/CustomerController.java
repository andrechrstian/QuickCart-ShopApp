package org.example.tokonyadia.controller;

import lombok.AllArgsConstructor;
import org.example.tokonyadia.constant.APIUrl;
import org.example.tokonyadia.dto.request.CustomerRequest;
import org.example.tokonyadia.dto.response.CustomerResponse;
import org.example.tokonyadia.dto.response.PageResponse;
import org.example.tokonyadia.entity.Customer;
import org.example.tokonyadia.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest request) {
        CustomerResponse createdCustomer = customerService.saveCustomer(request);
        return ResponseEntity.ok(createdCustomer);
    }

    @PutMapping
    public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody CustomerRequest request) {
        CustomerResponse createdCustomer = customerService.updateCustomer(request);
        return ResponseEntity.ok(createdCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        // block jika terjadi exception pada line 35
        return ResponseEntity.ok("Success Delete Customer By Id");
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable String id) {
        CustomerResponse customerResponse = customerService.getCustomerById(id);
        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping
    public ResponseEntity<PageResponse<CustomerResponse>> getAllCustomer(
            @RequestParam(name = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "size", defaultValue = "4") Integer pageSize,
            @RequestParam(name = "sortType", defaultValue = "ASC") String sortType,
            @RequestParam(name = "property", defaultValue = "name") String property,
            @RequestParam(name = "name", required = false)String name
    ){
        Sort sort = Sort.by(Sort.Direction.fromString(sortType),property);
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        CustomerRequest customerRequest = CustomerRequest.builder()
                .name(name)
                .build();

        Page<CustomerResponse> customerResponse = customerService.getCustomerPerPage(pageable, customerRequest);

        if (customerResponse.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        PageResponse<CustomerResponse> pageResponse = new PageResponse<>(customerResponse);
        return ResponseEntity.ok(pageResponse);

    }
}

