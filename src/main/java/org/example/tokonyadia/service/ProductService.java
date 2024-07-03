package org.example.tokonyadia.service;

import org.example.tokonyadia.entity.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct (Product product);
    List<Product> getAllProduct();
    Product updateProduct (Product updateProduct);
    Product deleteProduct (int id);
}
