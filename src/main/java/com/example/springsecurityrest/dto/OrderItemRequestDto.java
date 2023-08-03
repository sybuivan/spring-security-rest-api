package com.example.springsecurityrest.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class OrderItemRequestDto {

  private Long productId;
  private int quantity;
}
