package org.example.tokonyadia.service;

import org.example.tokonyadia.dto.request.TransactionRequest;
import org.example.tokonyadia.dto.response.TransactionResponse;

public interface TransactionService {
    TransactionResponse create(TransactionRequest transactionRequest);

}
