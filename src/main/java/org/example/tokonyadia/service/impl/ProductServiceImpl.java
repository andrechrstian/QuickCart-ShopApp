package org.example.tokonyadia.service.impl;

import lombok.AllArgsConstructor;
import org.example.tokonyadia.dto.request.ProductRequest;
import org.example.tokonyadia.dto.response.ProductResponse;
import org.example.tokonyadia.entity.Product;
import org.example.tokonyadia.repository.ProductRepository;
import org.example.tokonyadia.service.ProductService;
import org.example.tokonyadia.utils.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Override
    public ProductResponse saveProduct(ProductRequest request) {
        Product product = productRepository.saveAndFlush(
                Product.builder()
                        .id(request.getId())
                        .name(request.getName())
                        .price(request.getPrice())
                        .stock(request.getStock())
                        .build()
        );
        return convertToProductResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProduct(String name) {
        if (name != null) {
            return productRepository.findAllByNameLikeOrderByNameAsc("%" + name + "%");
        }
        return productRepository.findAll().stream().map(this::convertToProductResponse).toList();
    }

    @Override
    public ProductResponse getById(String id) {
        return convertToProductResponse(findByIdOrThrowNotFound(id));    }

    @Override
    public void deleteProduct(String id) {
        findByIdOrThrowNotFound(id);
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponse updateProduct(ProductRequest request) {
        findByIdOrThrowNotFound(request.getId());
        Product product = Product.builder()
                .id(request.getId())
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();

        return convertToProductResponse(productRepository.saveAndFlush(product));
    }

    @Override
    public ProductResponse updatePatch(ProductRequest request) {
        // Makesure data available
        findByIdOrThrowNotFound(request.getId());

        // Get existing data
        Product existingProduct = getProductById(request.getId());

        // Check data on request available
        if (request.getName() != null) existingProduct.setName(request.getName());
        if (request.getStock() != null) existingProduct.setStock(request.getStock());
        if (request.getPrice() != null) existingProduct.setPrice(request.getPrice());

        return convertToProductResponse(productRepository.saveAndFlush(existingProduct));

    }

    @Override
    public Product getProductById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    private Product findByIdOrThrowNotFound(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        return product;
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

