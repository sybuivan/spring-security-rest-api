package com.example.springsecurityrest.dto;

import com.example.springsecurityrest.models.OrderItem;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class OrderDto {
    private Long userId;
    private List<OrderItemRequestDto> orderItems;
}
