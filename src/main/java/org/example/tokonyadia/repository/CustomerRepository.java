package org.example.tokonyadia.repository;

import org.example.tokonyadia.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository <Customer, String> {

    List<Customer> findAllByNameLike(String name);

}
