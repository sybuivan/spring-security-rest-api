package com.example.springsecurityrest.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Data
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderId;
  private Date orderDate;
  private boolean status;
  private int totalPrice;

  @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST,
      CascadeType.REMOVE}, orphanRemoval = true)
  private List<OrderItem> orderItems;

  @ManyToOne
  @JoinColumn(name = "userId", nullable = false)
  private User user;
}
