package org.example.tokonyadia.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.tokonyadia.entity.Customer;
import org.example.tokonyadia.entity.TransactionDetail;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {
    private String id;
    private Customer customer;
    private Date date;
    private List<TransactionDetail> transactionDetail;
    private Long totalPayment;
    private String redirectUrl;
}
