package com.example.springsecurityrest.interfaces;

import com.example.springsecurityrest.dto.OrderDto;
import com.example.springsecurityrest.models.OrderItem;

import java.util.List;

public interface IOrder {
    void orderProduct(OrderDto orderDto);
}
