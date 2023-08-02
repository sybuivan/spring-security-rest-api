package com.example.springsecurityrest.api;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;


import com.example.springsecurityrest.interfaces.IProductService;
import org.mockito.ArgumentMatchers;
import com.example.springsecurityrest.controller.ProductController;
import com.example.springsecurityrest.models.Product;
import com.example.springsecurityrest.services.ProductServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


import java.util.Optional;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProductControllerApi {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private IProductService productService;
    @Autowired
    private ObjectMapper objectMapper;

    private Product product;

    @BeforeEach
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        product = new Product();
        product.setProductId(2L);
        product.setProductName("Honda 1");
        product.setPrice(1000);
    }

    @Test
    public void ProductController_ProductDetail_ReturnProduct() throws Exception {
        long productId = 1;

        when(productService.getProductById(productId)).thenReturn(Optional.ofNullable(product));

        ResultActions response = mockMvc.perform(get("/api/product/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productName", CoreMatchers.is(product.getProductName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.price", CoreMatchers.is(product.getPrice())));
    }

    @Test void ProductController_CreateProduct_ReturnedCreated() throws Exception {
        given(productService.addProduct(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productName", CoreMatchers.is(product.getProductName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.price", CoreMatchers.is(product.getPrice())));
    }
}
