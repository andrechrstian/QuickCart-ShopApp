package org.example.tokonyadia.service;

import org.example.tokonyadia.entity.Transaction;

public interface MidtransService {
    String createPayment (Transaction transaction, Long totalAmount);
}
