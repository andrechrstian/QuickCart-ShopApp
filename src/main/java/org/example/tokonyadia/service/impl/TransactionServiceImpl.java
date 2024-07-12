package org.example.tokonyadia.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.tokonyadia.dto.request.TransactionRequest;
import org.example.tokonyadia.dto.response.TransactionResponse;
import org.example.tokonyadia.entity.Customer;
import org.example.tokonyadia.entity.Product;
import org.example.tokonyadia.entity.Transaction;
import org.example.tokonyadia.entity.TransactionDetail;
import org.example.tokonyadia.repository.TransactionDetailRepository;
import org.example.tokonyadia.repository.TransactionRepository;
import org.example.tokonyadia.service.CustomerService;
import org.example.tokonyadia.service.ProductService;
import org.example.tokonyadia.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDetailRepository transactionDetailRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse create(TransactionRequest transactionRequest) {

        Customer customer = customerService.getById(transactionRequest.getCustomerId());
        Transaction transaction = Transaction.builder()
                .customer(customer)
                .build();

        AtomicReference<Long> totalPayment = new AtomicReference<>(0L);

        List<TransactionDetail> transactionDetail = transactionRequest.getTransactionDetail().stream()
                .map(detailRequest -> {
            Product product = productService.getProductById(detailRequest.getProductId());
            if (product.getStock() - detailRequest.getQty() < 0) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "out of stock");
            }
            product.setStock((product.getStock() - detailRequest.getQty()));

            TransactionDetail trxDetail = TransactionDetail.builder()
                    .product(product)
                    .transaction(transaction)
                    .qty(detailRequest.getQty())
                    .productPrice(product.getPrice())
                    .build();

            //TODO : Total Payment
            totalPayment.updateAndGet(v -> v + product.getPrice() * detailRequest.getQty());

            //TODO : Insert Transaction Detail
            transactionDetailRepository.save(trxDetail);
            return trxDetail;
        }).toList();

        //TODO : Insert Transaction
        transaction.setTransactionDetails(transactionDetail);
        Transaction resultTransaction = transactionRepository.saveAndFlush(transaction);

        //TODO : Create Transaction Response
        return TransactionResponse.builder()
                .id(resultTransaction.getId())
                .customer(resultTransaction.getCustomer())
                .transactionDetail(resultTransaction.getTransactionDetails())
                .totalPayment(totalPayment.get())
                .date(resultTransaction.getDate())
                .build();
    }
}
