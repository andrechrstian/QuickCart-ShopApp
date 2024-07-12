package org.example.tokonyadia.repository;

import org.example.tokonyadia.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
