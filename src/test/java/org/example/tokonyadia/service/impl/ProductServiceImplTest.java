package org.example.tokonyadia.service.impl;

import org.example.tokonyadia.dto.request.ProductRequest;
import org.example.tokonyadia.dto.response.ProductResponse;
import org.example.tokonyadia.entity.Product;
import org.example.tokonyadia.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @BeforeEach
    void beforeEach() {

    }

    @Test
    @DisplayName("Test Save product success")
    void saveProduct() {
        ProductRequest productRequest = ProductRequest.builder()
                .name("product_test")
                .stock(5)
                .price(1000L)
                .build();

        ProductResponse expectedResponse = ProductResponse.builder()
                .id("test")
                .name("product_test")
                .stock(5)
                .price(1000L)
                .build();

        Product product = Product.builder()
                .id(expectedResponse.getId())
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .build();

        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(product);
        ProductResponse actualProduct = productService.saveProduct(productRequest);

        assertNotNull(actualProduct);
        assertEquals(expectedResponse, actualProduct);
    }

    @Test
    @DisplayName("Test Save Product Fail")
    void createFail() {
        ProductRequest request = new ProductRequest();
        when(productRepository.saveAndFlush(any(Product.class))).thenThrow(new DataIntegrityViolationException("Failed to save product"));
        try {
            productService.saveProduct(request);
            verify(productRepository, times(1)).saveAndFlush(any(Product.class));
            fail();
        } catch (DataIntegrityViolationException e) {
            assertEquals("Failed to save product", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test Get All Product Success")
    void getAllProduct() {
        List<Product> products = List.of(
                Product.builder().id("1").name("product1").stock(10).price(100L).build(),
                Product.builder().id("2").name("product2").stock(20).price(200L).build()
        );

        when(productRepository.findAll()).thenReturn(products);

        List<ProductResponse> actualProducts = productService.getAllProduct(null);

        assertNotNull(actualProducts);
        assertEquals(2, actualProducts.size());
        assertEquals("product1", actualProducts.get(0).getName());
        assertEquals("product2", actualProducts.get(1).getName());
    }

    @Test
    @DisplayName("Test Get All Product Fail")
    void getAllProductFail() {
        when(productRepository.findAll()).thenThrow(new RuntimeException("Database error"));
        try {
            productService.getAllProduct(null);
            verify(productRepository, times(1)).findAll();
            fail();
        } catch (RuntimeException e) {
            assertEquals("Database error", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test Get Product By Id Success")
    void getById() {
        String productId = "1";
        Product product = Product.builder().id(productId).name("product1").stock(10).price(100L).build();

        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));

        ProductResponse actualProduct = productService.getById(productId);
        assertNotNull(actualProduct);
        assertEquals(productId, actualProduct.getId());
        assertEquals("product1", actualProduct.getName());
        assertEquals(10, actualProduct.getStock());
        assertEquals(100L, actualProduct.getPrice());
    }

    @Test
    @DisplayName("Test Get Product by Id Fail")
    void getByIdFail() {
        String productId = "1";

        when(productRepository.findById(productId)).thenReturn(java.util.Optional.empty());
        try {
            productService.getById(productId);
            verify(productRepository, times(1)).findById(productId);
            fail();
        } catch (RuntimeException e) {
            assertEquals("Not Found", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test Update Product Success")
    void updateProduct() {
        ProductRequest request = ProductRequest.builder()
                .id("1")
                .name("updated_product")
                .stock(15)
                .price(150L)
                .build();

        Product existingProduct = Product.builder()
                .id("1")
                .name("product1")
                .stock(10)
                .price(100L)
                .build();

        Product updatedProduct = Product.builder()
                .id("1")
                .name("updated_product")
                .stock(15)
                .price(150L)
                .build();

        when(productRepository.findById(request.getId())).thenReturn(java.util.Optional.of(existingProduct));
        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(updatedProduct);

        ProductResponse actualProduct = productService.updateProduct(request);

        assertNotNull(actualProduct);
        assertEquals(request.getId(), actualProduct.getId());
        assertEquals(request.getName(), actualProduct.getName());
        assertEquals(request.getStock(), actualProduct.getStock());
        assertEquals(request.getPrice(), actualProduct.getPrice());
    }

    @Test
    @DisplayName("Test Update Product Fail")
    void UpdateProductFail() {
        ProductRequest request = ProductRequest.builder()
                .id("1")
                .name(null)
                .stock(-1)
                .price(0L)
                .build();

        Product existingProduct = Product.builder()
                .id("1")
                .name("product1")
                .stock(10)
                .price(100L)
                .build();

        when(productRepository.findById(request.getId())).thenReturn(java.util.Optional.of(existingProduct));
        when(productRepository.saveAndFlush(any(Product.class))).thenThrow(new RuntimeException("Validation error"));
        try {
            productService.updateProduct(request);
            verify(productRepository, times(1)).saveAndFlush(any(Product.class));
            fail();
        } catch (RuntimeException e) {
            assertEquals("Validation error", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test Update Product Patch Success")
    void updatePatch() {
        ProductRequest request = ProductRequest.builder()
                .id("1")
                .name("patched_product")
                .stock(20)
                .price(200L)
                .build();

        Product existingProduct = Product.builder()
                .id("1")
                .name("product1")
                .stock(10)
                .price(100L)
                .build();

        Product patchedProduct = Product.builder()
                .id("1")
                .name("patched_product")
                .stock(20)
                .price(200L)
                .build();

        when(productRepository.findById(request.getId())).thenReturn(java.util.Optional.of(existingProduct));
        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(patchedProduct);

        ProductResponse actualProduct = productService.updatePatch(request);
        assertNotNull(actualProduct);
        assertEquals(request.getId(), actualProduct.getId());
        assertEquals(request.getName(), actualProduct.getName());
        assertEquals(request.getStock(), actualProduct.getStock());
        assertEquals(request.getPrice(), actualProduct.getPrice());
    }

    @Test
    @DisplayName("Test Update Product Patch Success")
    void updatePatchFail() {
        ProductRequest request = ProductRequest.builder()
                .id("1")
                .name(null)
                .stock(-1)
                .price(0L)
                .build();

        Product existingProduct = Product.builder()
                .id("1")
                .name("product1")
                .stock(10)
                .price(100L)
                .build();

        when(productRepository.findById(request.getId())).thenReturn(java.util.Optional.of(existingProduct));
        when(productRepository.saveAndFlush(any(Product.class))).thenThrow(new RuntimeException("Validation error"));
        try {
            productService.updatePatch(request);
            verify(productRepository, times(1)).saveAndFlush(any(Product.class));
            fail();
        } catch (RuntimeException e) {
            assertEquals("Validation error", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test Delete Product Success")
    void deleteProduct() {
        String productId = "1";
        Product product = Product.builder()
                .id(productId)
                .name("product1")
                .stock(10)
                .price(100L)
                .build();

        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));
        doNothing().when(productRepository).deleteById(productId);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    @DisplayName("Test Delete Product Fail")
    void deleteProductFail() {
        String productId = "1";

        when(productRepository.findById(productId)).thenReturn(java.util.Optional.empty());
        try {
            productService.deleteProduct(productId);
            verify(productRepository, times(1)).findById(productId);
            fail();
        } catch (RuntimeException e) {
            assertEquals("Not Found", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test Get Product By Id")
    void getProductById() {
        String productId = "1";
        Product product = Product.builder().id(productId).name("product1").stock(10).price(100L).build();

        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));

        ProductResponse actualProduct = productService.getById(productId);

        assertNotNull(actualProduct);
        assertEquals(productId, actualProduct.getId());
        assertEquals("product1", actualProduct.getName());
        assertEquals(10, actualProduct.getStock());
        assertEquals(100L, actualProduct.getPrice());
    }
}