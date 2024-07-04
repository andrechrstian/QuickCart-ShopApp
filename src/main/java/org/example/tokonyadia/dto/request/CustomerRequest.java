package org.example.tokonyadia.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data //Setter Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    private String name;
    private String address;
    private String phone;
    private Date birthDate;
}
