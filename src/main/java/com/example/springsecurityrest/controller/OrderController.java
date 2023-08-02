package com.example.springsecurityrest.controller;

import com.example.springsecurityrest.dto.OrderDto;
import com.example.springsecurityrest.interfaces.IOrder;
import com.example.springsecurityrest.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private IOrder orderService;

    @PostMapping("")
    public ResponseEntity<?> orderProduct(@RequestBody OrderDto orderDto) {
        orderService.orderProduct(orderDto);

        return ResponseEntity.ok().body("null");
    }

}
