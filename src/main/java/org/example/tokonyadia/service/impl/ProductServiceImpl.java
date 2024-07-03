package org.example.tokonyadia.service.impl;

import org.example.tokonyadia.entity.Product;
import org.example.tokonyadia.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    List<Product> dbProduct = new ArrayList<Product>();

    @Override
    public Product saveProduct(Product product) {

        //save product on list memory
        dbProduct.add(product);
        return product;
    }

    @Override
    public List<Product> getAllProduct() {
        return dbProduct;
    }


    @Override
    public Product deleteProduct(int id) {
        for (int i = 0; i < dbProduct.size(); i++) {
            Product product = dbProduct.get(i);
            if (product.getId() == id) {
                dbProduct.remove(i);
                return product;
            }
        }
        return null;
    }

    @Override
    public Product updateProduct(Product updateProduct) {
        for (int i = 0; i < dbProduct.size(); i++) {
            Product product = dbProduct.get(i);
            if (product.getId() == updateProduct.getId()) {
                dbProduct.set(i,updateProduct);
                return updateProduct;
            }
        }
        return null;
    }

}

