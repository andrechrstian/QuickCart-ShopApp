package org.example.tokonyadia.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.tokonyadia.constant.APIUrl;
import org.example.tokonyadia.dto.request.TransactionRequest;
import org.example.tokonyadia.dto.response.TransactionResponse;
import org.example.tokonyadia.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping(path = APIUrl.TRANSACTION_API)
public class PurchaseController {

    private TransactionService transactionService;

    @PostMapping
    public TransactionResponse savePurchase(@RequestBody TransactionRequest transactionRequest){
        return transactionService.create(transactionRequest);
    }

}
