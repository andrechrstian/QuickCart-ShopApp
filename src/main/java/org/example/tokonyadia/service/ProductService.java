package org.example.tokonyadia.service;

import org.example.tokonyadia.dto.request.ProductRequest;
import org.example.tokonyadia.dto.response.ProductResponse;
import org.example.tokonyadia.entity.Product;

import java.util.List;

public interface ProductService {
    ProductResponse saveProduct (ProductRequest product);
    List<ProductResponse> getAllProduct(String name);
    ProductResponse getById(String id);
    ProductResponse updateProduct (ProductRequest update);
    ProductResponse updatePatch(ProductRequest request);
    void deleteProduct (String id);
    Product getProductById (String id);
}
