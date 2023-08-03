package com.example.springsecurityrest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class OrderDto {

  @NotNull(message = "Userid not empty")
  private Long userId;

  @Size.List({
      @Size(min = 1, message = "orderItems not empty")
  })
  private List<OrderItemRequestDto> orderItems;
}
