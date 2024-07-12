package org.example.tokonyadia.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "m_customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "phone")
    private String phone;
    @Column(name = "birth_date")
    private Date birthDate;
    @Column(name = "status")
    private boolean deleted = Boolean.FALSE;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}


