package org.example.tokonyadia.service;

import org.example.tokonyadia.entity.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct (Product product);
    List<Product> getAllProduct(String name);
    Product getById(String id);
    Product updateProduct (Product update);
    void deleteProduct (String id);
}
