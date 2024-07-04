package org.example.tokonyadia.controller;

import lombok.AllArgsConstructor;
import org.example.tokonyadia.constant.APIUrl;
import org.example.tokonyadia.entity.Product;
import org.example.tokonyadia.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = APIUrl.PRODUCT_API)
public class ProductController {

    private ProductService productService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Product postProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping
    public List<Product> getProduct(@RequestParam(name = "name", required = false) String name) {
        return productService.getAllProduct(name);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody Product product) {
        try {
            product.setId(id);
            Product updateProduct = productService.updateProduct(product);
            return ResponseEntity.ok(updateProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
