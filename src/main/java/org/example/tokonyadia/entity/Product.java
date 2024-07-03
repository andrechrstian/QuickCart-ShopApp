package org.example.tokonyadia.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private Integer price;
    private Integer stock;
    private boolean deleted = false;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", deleted=" + deleted +
                '}';
    }
}


