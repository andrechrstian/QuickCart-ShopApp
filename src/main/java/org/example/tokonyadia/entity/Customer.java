package org.example.tokonyadia.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.Temporal;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class Customer {
    private String id;
    private String fullName;
    private String address;
    private String phone;
    private Date birthDate;
    private boolean deleted = Boolean.FALSE;

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", birthDate=" + birthDate +
                ", deleted=" + deleted +
                '}';
    }
}


