package org.example.tokonyadia.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "Authorization")
public class ProductController {
    private ProductService productService;

    @PostMapping
    public ResponseEntity<CommonResponse<ProductResponse>> createNewProduct(@RequestBody ProductRequest payload) {
        ProductResponse product = productService.saveProduct(payload);

        CommonResponse<ProductResponse> response = generateProductResponse(
                HttpStatus.OK.value(), "New product added!", Optional.of(product));
        return ResponseEntity.ok(response);
    }

    // /api/product?name=laptop
    @GetMapping
    public ResponseEntity<CommonResponse<List<ProductResponse>>> getAllProduct(
            @RequestParam(name = "name", required = false) String name
    ) {
        List<ProductResponse> productList = productService.getAllProduct(name);

        CommonResponse<List<ProductResponse>> response = CommonResponse.<List<ProductResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("All product data")
                .data(Optional.of(productList))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}") // /api/product/{UUID}
    public ResponseEntity<CommonResponse<ProductResponse>> getProductById(@PathVariable String id) {
        ProductResponse product = productService.getById(id);
        CommonResponse<ProductResponse> response = generateProductResponse(HttpStatus.OK.value(),
                "", Optional.of(product));
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<ProductResponse>> updateProduct(@RequestBody ProductRequest payload) {
        ProductResponse product = productService.updateProduct(payload);

        CommonResponse<ProductResponse> response = generateProductResponse(
                HttpStatus.OK.value(), "Product Updated", Optional.of(product));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<ProductResponse>> deleteProductById(@PathVariable String id) {
        productService.deleteProduct(id);
        CommonResponse<ProductResponse> response = generateProductResponse(HttpStatus.OK.value(), "Success Delete Product by id", Optional.empty());
        return ResponseEntity.ok(response);
    }

    @PatchMapping()
    public ResponseEntity<CommonResponse<ProductResponse>> updateStock(@RequestBody ProductRequest payload) {
        productService.updateProduct(payload);
        CommonResponse<ProductResponse> response = generateProductResponse(HttpStatus.OK.value(), "Success update stock product by id", Optional.empty());
        return ResponseEntity.ok(response);
    }

    private CommonResponse<ProductResponse> generateProductResponse(Integer code, String message, Optional<ProductResponse> productResponse) {
        return CommonResponse.<ProductResponse>builder()
                .statusCode(code)
                .message(message)
                .data(productResponse)
                .build();
    }
}
