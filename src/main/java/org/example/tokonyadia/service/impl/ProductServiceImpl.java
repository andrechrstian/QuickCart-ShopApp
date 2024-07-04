package org.example.tokonyadia.service.impl;

import lombok.AllArgsConstructor;
import org.example.tokonyadia.entity.Product;
import org.example.tokonyadia.repository.ProductRepository;
import org.example.tokonyadia.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Override
    public List<Product> getAllProduct(String name) {
       if (name != null) {
           return productRepository.findAllByNameLike("%" + name + "%");
       }
        return productRepository.findAll();
    }

    @Override
    public Product getById(String id) {
        // optional = list/satuan
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product updateProduct(Product update) {
        //getById(updateProduct.getId());
        if (!productRepository.existsById((update.getId()))) {
            throw new IllegalArgumentException("Product Id " + update.getId() + " does not exixst");
        }
        return productRepository.saveAndFlush(update);
    }

}

