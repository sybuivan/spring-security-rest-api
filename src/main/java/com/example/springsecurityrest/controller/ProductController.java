package com.example.springsecurityrest.controller;

import com.example.springsecurityrest.constants.MessageEnum;
import com.example.springsecurityrest.constants.StatusEnum;
import com.example.springsecurityrest.interfaces.IRefreshToken;
import com.example.springsecurityrest.models.Product;
import com.example.springsecurityrest.payload.response.ResponseObject;
import com.example.springsecurityrest.interfaces.IProductService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
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
  IProductService productService;

  @Autowired
  private IRefreshToken refreshToken;

  @GetMapping("")
  ResponseEntity<ResponseObject> getProductList(
      @RequestParam(required = false, defaultValue = "") String productName) {
    List<Product> productList = productService.getProductList(productName);
    ResponseObject responseObject = new ResponseObject(
        MessageEnum.GET.getFormattedMessage("product list"),
        StatusEnum.SUCCESS, productList);
    return ResponseEntity.status(HttpStatus.OK).body(responseObject);
  }

  @PostMapping("")
  @RolesAllowed("ROLE_ADMIN")
  ResponseEntity<ResponseObject> postProduct(@RequestBody Product product) {

    List<Product> productList = productService.findByProductNameAndPrice(
        product.getProductName(), product.getPrice()
    );
    if (productList.size() == 0) {
      ResponseObject responseObject = new ResponseObject(
          MessageEnum.CREATE.getFormattedMessage("product"),
          StatusEnum.SUCCESS, productService.addProduct(product));

      return ResponseEntity.status(HttpStatus.CREATED).body(responseObject);
    } else {
      ResponseObject responseObject = new ResponseObject("Product already exists",
          StatusEnum.FAILED);

      return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(responseObject);
    }
  }

  @GetMapping("/{productId}")
  ResponseEntity<?> getProductById(@PathVariable Long productId) {
    Optional<Product> optionalProduct = productService.getProductById(productId);

    if (optionalProduct.isPresent()) {
      Product productPresent = optionalProduct.get();
      return ResponseEntity.ok(
          new ResponseObject(MessageEnum.GET.getFormattedMessage("product"), StatusEnum.SUCCESS,
              productPresent));
//            return ResponseEntity.ok(productPresent);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
          MessageEnum.NOT_FOUND.getFormattedMessage("product", productId), StatusEnum.FAILED
      ));
    }
  }

  @DeleteMapping("/{productId}")
  ResponseEntity<ResponseObject> deleteProductById(@PathVariable Long productId) {
    Optional<Product> optionalProduct = productService.getProductById(productId);

    if (optionalProduct.isPresent()) {
      productService.deleteProductById(productId);
      return ResponseEntity.ok(new ResponseObject(MessageEnum.DELETE.getFormattedMessage("product"),
          StatusEnum.SUCCESS));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
          MessageEnum.NOT_FOUND.getFormattedMessage("product", productId), StatusEnum.FAILED
      ));
    }
  }

  @PutMapping("/{productId}")
  ResponseEntity<?> updateProductById(@PathVariable Long productId,
      @Valid @RequestBody Product product) {
    return ResponseEntity.status(HttpStatus.OK).body(
        new ResponseObject(MessageEnum.UPDATE.getFormattedMessage("product"), StatusEnum.SUCCESS,
            productService.updateProduct(productId, product)));
  }
}
