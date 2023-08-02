package com.example.springsecurityrest.specifications;

import com.example.springsecurityrest.models.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications implements Specification<Product> {

    private final String productName;

    public ProductSpecifications(String productName) {
        this.productName = productName;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(root.get("productName"), "%" + productName + "%");
    }
}
