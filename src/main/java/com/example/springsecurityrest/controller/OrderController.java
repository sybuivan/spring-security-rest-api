package com.example.springsecurityrest.controller;

import com.example.springsecurityrest.constants.StatusEnum;
import com.example.springsecurityrest.dto.OrderDto;
import com.example.springsecurityrest.interfaces.IOrderService;
import com.example.springsecurityrest.models.OrderItem;
import com.example.springsecurityrest.payload.response.ResponseObject;
import jakarta.validation.Valid;
import java.util.List;
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
  private IOrderService orderService;

  @PostMapping("")
  public ResponseEntity<?> orderProduct(@Valid @RequestBody OrderDto orderDto) {
    List<OrderItem> orderItemList = orderService.orderProduct(orderDto);

    return ResponseEntity.ok().body(new ResponseObject("/api/order success", StatusEnum.SUCCESS));
  }

}
