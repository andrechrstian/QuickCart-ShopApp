package org.example.tokonyadia.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.tokonyadia.entity.Transaction;
import org.example.tokonyadia.entity.TransactionDetail;
import org.example.tokonyadia.service.MidtransService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MidtransServiceImpl implements MidtransService {

    private final RestTemplate restTemplate;

    private final String SERVER_KEY = "SB-Mid-server-aVmSQnPk0bRwX2mTREkFZ4R7";
    private final String MIDTRANS_URL = "https://app.sandbox.midtrans.com/snap/v1/transactions";

    @Override
    public String createPayment(Transaction transaction, Long totalAmount) {

        HttpHeaders headers = createBaseAuthHeader();

        Map<String, Object> transactionDetails = new HashMap<>();
        transactionDetails.put("order_id", transaction.getId());
        transactionDetails.put("gross_amount", totalAmount);

        Map<String, Object> customerDetails = new HashMap<>();
        customerDetails.put("customer_id", transaction.getCustomer().getId());

        List<Map<String, Object>> itemDetails = new ArrayList<>();
        long calculatedTotalAmount = 0;
        for (TransactionDetail detail : transaction.getTransactionDetails()) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", detail.getProduct().getId());
            item.put("name",detail.getProduct().getName());
            item.put("quantity", detail.getQty());
            item.put("price", detail.getProductPrice());
            itemDetails.add(item);
            calculatedTotalAmount += detail.getQty() * detail.getProductPrice();
        }

        if (totalAmount != calculatedTotalAmount) {
            throw  new IllegalArgumentException("Total amount tidak match");
        }

        Map<String, Object> payload = new HashMap<>();
        payload.put("transaction_details", transactionDetails);
        payload.put("customer_details", customerDetails);
        payload.put("item_details", itemDetails);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);
        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                MIDTRANS_URL,
                HttpMethod.POST,
                requestEntity,
                Map.class);
        Map responseBody = responseEntity.getBody();
        System.out.println(responseBody);
        return (String) responseBody.get("redirect_url");
    }

    private HttpHeaders createBaseAuthHeader() {
        String auth = SERVER_KEY + ":";
        byte[] encodeAuth = Base64.getEncoder().encode(auth.getBytes());
        String authHeader = "Basic " + new String(encodeAuth);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", authHeader);

        return headers;
    }

}
