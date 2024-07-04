package org.example.tokonyadia.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor //menghasilkan cinstructor yang menerima argumen
@NoArgsConstructor // Cinstructor yang tidak menerima argume
@Entity //Entitas JPA yang akan dipetakan
@Table(name = "m_product") //table "m_product"
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "price", nullable = false, columnDefinition = "BIGINT CHECK (price >= 0)")
    private Long price;
    @Column(name = "stock", nullable = false, columnDefinition = "INT CHECK (stock >= 0)")
    private Integer stock;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}


