package org.example.tokonyadia.service;

import org.example.tokonyadia.dto.request.ProductRequest;
import org.example.tokonyadia.dto.response.ProductResponse;
import org.example.tokonyadia.entity.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct (ProductRequest product);
    List<Product> getAllProduct(String name);
    Product getById(String id);
    Product updateProduct (ProductRequest update);
    Product updatePatch(ProductRequest request);
    void deleteProduct (String id);
    Product getProductById (String id);
}
