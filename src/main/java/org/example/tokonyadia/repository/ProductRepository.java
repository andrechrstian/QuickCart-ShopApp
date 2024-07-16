package org.example.tokonyadia.repository;

import org.example.tokonyadia.dto.response.PageResponse;
import org.example.tokonyadia.dto.response.ProductResponse;
import org.example.tokonyadia.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository <Product, String> {

    List<ProductResponse> findAllByNameLikeOrderByNameAsc(String name);

}
