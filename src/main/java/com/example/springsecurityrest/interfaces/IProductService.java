package com.example.springsecurityrest.interfaces;

import com.example.springsecurityrest.models.Product;
import java.util.List;
import java.util.Optional;

public interface IProductService {
    Product addProduct(Product product);
    Product updateProduct(Long productId, Product product);

    List<Product> getProductList(String productName);

    Optional<Product> getProductById(Long id);
    void deleteProductById(Long id);

    List<Product> findByProductNameAndPrice(String productName, int price);
}
