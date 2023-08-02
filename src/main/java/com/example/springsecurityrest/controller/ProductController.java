package com.example.springsecurityrest.controller;

import com.example.springsecurityrest.constants.Status;
import com.example.springsecurityrest.interfaces.IRefreshToken;
import com.example.springsecurityrest.models.Product;
import com.example.springsecurityrest.payload.response.ResponseObject;
import com.example.springsecurityrest.interfaces.IProductService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    IProductService IProductService;

    @Autowired
    private IRefreshToken refreshToken;

    @GetMapping("")
    ResponseEntity<ResponseObject>  getProductList(@RequestParam(required = false, defaultValue = "") String productName) {
        List<Product> productList = IProductService.getProductList(productName);
        ResponseObject responseObject = new ResponseObject("Get product list successfully",
                Status.SUCCESS,productList);
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }

    @PostMapping("")
    @RolesAllowed("ROLE_ADMIN")
    ResponseEntity<ResponseObject> postProduct(@RequestBody Product product) {

        List<Product> productList = IProductService.findByProductNameAndPrice(
                product.getProductName(), product.getPrice()
        );
        if(productList.size() == 0) {
            ResponseObject responseObject = new ResponseObject("Save product successfully",
                    Status.SUCCESS, IProductService.addProduct(product));

            return ResponseEntity.status(HttpStatus.CREATED).body(responseObject);
        } else {
            ResponseObject responseObject = new ResponseObject("Product already exists",
                    Status.FAILED);

            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(responseObject);
        }
    }

    @GetMapping("/{productId}")
    ResponseEntity<?> getProductById(@PathVariable Long productId) {
        Optional<Product> optionalProduct = IProductService.getProductById(productId);

        if(optionalProduct.isPresent()) {
            Product productPresent = optionalProduct.get();
            return ResponseEntity.ok(new ResponseObject("Product get success.", Status.SUCCESS, productPresent));
//            return ResponseEntity.ok(productPresent);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                    "Product with ID " + productId + " not found." , Status.FAILED
            ));
        }
    }

    @DeleteMapping("/{productId}")
    ResponseEntity<ResponseObject> deleteProductById(@PathVariable Long productId) {
        Optional<Product> optionalProduct = IProductService.getProductById(productId);

        if(optionalProduct.isPresent()) {
            Product productPresent = optionalProduct.get();
            return ResponseEntity.ok(new ResponseObject("Product delete success.", Status.SUCCESS));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                    "Product with ID " + productId + " not found." , Status.FAILED
            ));
        }
    }

    @PutMapping("/{productId}")
    ResponseEntity<?> updateProductById(@PathVariable Long productId, @RequestBody Product product) {

        Optional<Product> optionalProduct = IProductService.getProductById(productId);
        if(optionalProduct.isPresent()) {
            
        }

        return null;
    }
}
