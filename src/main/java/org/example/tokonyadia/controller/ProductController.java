package org.example.tokonyadia.controller;

import lombok.AllArgsConstructor;
import org.example.tokonyadia.constant.APIUrl;
import org.example.tokonyadia.dto.request.CustomerRequest;
import org.example.tokonyadia.dto.request.ProductRequest;
import org.example.tokonyadia.dto.response.CommonResponse;
import org.example.tokonyadia.dto.response.CustomerResponse;
import org.example.tokonyadia.dto.response.ProductResponse;
import org.example.tokonyadia.entity.Product;
import org.example.tokonyadia.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(path = APIUrl.PRODUCT_API)
public class ProductController {
    private ProductService productService;

    @PostMapping
    public ResponseEntity<CommonResponse<Product>> createNewProduct(@RequestBody ProductRequest payload) {
        Product product = productService.saveProduct(payload);

        CommonResponse<Product> response = generateProductResponse(HttpStatus.OK.value(), "New product added!", Optional.of(product));
        return ResponseEntity.ok(response);
    }

    // /api/product?name=laptop
    @GetMapping
    public ResponseEntity<CommonResponse<List<Product>>> getAllProduct(
            @RequestParam(name = "name", required = false) String name
    ) {
        List<Product> productList = productService.getAllProduct(name);

        CommonResponse<List<Product>> response = CommonResponse.<List<Product>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("All product data")
                .data(Optional.of(productList))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}") // /api/product/{UUID}
    public ResponseEntity<CommonResponse<Product>> getProductById(@PathVariable String id) {
        Product product = productService.getById(id);
        CommonResponse<Product> response = generateProductResponse(HttpStatus.OK.value(), "", Optional.of(product));
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<Product>> updateProduct(@RequestBody ProductRequest payload) {
        Product product = productService.updateProduct(payload);
        CommonResponse<Product> response = generateProductResponse(HttpStatus.OK.value(), "Product Updated", Optional.of(product));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Product>> deleteProductById(@PathVariable String id) {
        productService.deleteProduct(id);
        CommonResponse<Product> response = generateProductResponse(HttpStatus.OK.value(), "Success Delete Product by id", Optional.empty());
        return ResponseEntity.ok(response);
    }

    @PatchMapping()
    public ResponseEntity<CommonResponse<Product>> updateStock(@RequestBody ProductRequest payload) {
        productService.updateProduct(payload);
        CommonResponse<Product> response = generateProductResponse(HttpStatus.OK.value(), "Success update stock product by id", Optional.empty());
        return ResponseEntity.ok(response);
    }

    private CommonResponse<Product> generateProductResponse(Integer code, String message, Optional<Product> product) {
        return CommonResponse.<Product>builder()
                .statusCode(code)
                .message(message)
                .data(product)
                .build();
    }
}
