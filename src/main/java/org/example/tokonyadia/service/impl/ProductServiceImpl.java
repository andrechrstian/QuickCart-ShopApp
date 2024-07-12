package org.example.tokonyadia.service.impl;

import lombok.AllArgsConstructor;
import org.example.tokonyadia.dto.request.ProductRequest;
import org.example.tokonyadia.dto.response.CustomerResponse;
import org.example.tokonyadia.dto.response.ProductResponse;
import org.example.tokonyadia.entity.Customer;
import org.example.tokonyadia.entity.Product;
import org.example.tokonyadia.repository.ProductRepository;
import org.example.tokonyadia.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Override
    public Product saveProduct(ProductRequest request) {
        Product product = Product.builder()
                        .id(request.getId())
                        .name(request.getName())
                        .price(request.getPrice())
                        .stock(request.getStock())
                        .build();

        return productRepository.saveAndFlush(product);
    }

    @Override
    public List<Product> getAllProduct(String name) {
        if (name != null) {
            return productRepository.findAllByNameLikeOrderByNameAsc("%" + name + "%");
        }
        return productRepository.findAll();

    }

    @Override
    public Product getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public void deleteProduct(String id) {
        findByIdOrThrowNotFound(id);
        productRepository.deleteById(id);
    }

    @Override
    public Product updateProduct(ProductRequest request) {
        findByIdOrThrowNotFound(request.getId());
        Product product = Product.builder()
                        .id(request.getId())
                        .name(request.getName())
                        .price(request.getPrice())
                        .stock(request.getStock())
                        .build();

        return productRepository.saveAndFlush(product);
    }

    @Override
    public Product updatePatch(ProductRequest request) {
        // Makesure data available
        findByIdOrThrowNotFound(request.getId());

        // Get existing data
        Product existingProduct = getById(request.getId());

        // Check data on request available
        if (request.getName() != null) existingProduct.setName(request.getName());
        if (request.getStock() != null) existingProduct.setStock(request.getStock());
        if (request.getPrice() != null) existingProduct.setPrice(request.getPrice());

        return productRepository.saveAndFlush(existingProduct);

    }

    @Override
    public Product getProductById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    private Product findByIdOrThrowNotFound(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
    }

    private ProductResponse convertToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

}

