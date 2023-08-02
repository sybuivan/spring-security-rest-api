package com.example.springsecurityrest.repositories;

import com.example.springsecurityrest.models.Product;
import com.example.springsecurityrest.specifications.ProductSpecifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>{
    List<Product> findByProductNameAndPrice(String productName, int price);
}
