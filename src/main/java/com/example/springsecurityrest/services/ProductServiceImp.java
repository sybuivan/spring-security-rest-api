package com.example.springsecurityrest.services;

import com.example.springsecurityrest.constants.MessageEnum;
import com.example.springsecurityrest.exception.ResourceNotFoundException;
import com.example.springsecurityrest.interfaces.IProductService;
import com.example.springsecurityrest.models.Product;
import com.example.springsecurityrest.models.UserWebClient;
import com.example.springsecurityrest.repositories.ProductRepository;
import com.example.springsecurityrest.specifications.ProductSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements IProductService {

    @Autowired
    ProductRepository productRepository;
//    private final WebClient webClient;
//    public ProductServiceImp() {
//        this.webClient = WebClient.create("https://jsonplaceholder.typicode.com");
//    }
//
//    public Flux<UserWebClient> getUsers() {
//        return webClient.get().uri("/users").retrieve()
//                .bodyToFlux(UserWebClient.class);
//    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        Product exitstingProduct = productRepository.findById(productId).orElseThrow(() ->
            new ResourceNotFoundException(MessageEnum.NOT_FOUND.getFormattedMessage("product", productId)));

        exitstingProduct.setProductName(product.getProductName());
        exitstingProduct.setPrice(product.getPrice());
        exitstingProduct.setDescription(product.getDescription());

        return productRepository.save(exitstingProduct);
    }

    @Override
    public List<Product> getProductList(String productName) {
        Specification<Product> spec = new ProductSpecifications(productName);
        return productRepository.findAll(spec);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findByProductNameAndPrice(String productName, int price) {
        return productRepository.findByProductNameAndPrice(productName, price);
    }
}
