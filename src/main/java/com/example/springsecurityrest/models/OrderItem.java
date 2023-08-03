package com.example.springsecurityrest.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class OrderItem {

  private int quantity;

  @Id
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "orderId", nullable = false)
  private Order order;

  @Id
  @ManyToOne
  @JoinColumn(name = "productId", nullable = false)
  private Product product;
}
