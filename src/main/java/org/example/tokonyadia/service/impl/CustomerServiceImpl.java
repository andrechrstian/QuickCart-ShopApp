package org.example.tokonyadia.service.impl;

import org.example.tokonyadia.entity.Customer;
import org.example.tokonyadia.entity.Product;
import org.example.tokonyadia.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    List<Customer> customers = new ArrayList<Customer>();

    @Override
    public Customer saveCustomer(Customer customer) {
        customers.add(customer);
        return customer;
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customers;
    }

    @Override
    public Customer deleteCustomer(int id) {
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            if (customer.getId().equals(id)) {
                customers.remove(i);
                return customer;
            }
        }
        return null;
    }

    @Override
    public Customer updateCustomer(Customer updateCustomer) {
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            if (customer.getId().equals(updateCustomer.getId())) {
                customers.set(i,updateCustomer);
                return updateCustomer;
            }
        }
        return null;
    }
}
