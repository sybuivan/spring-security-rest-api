package com.example.springsecurityrest.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productId;

  @NotBlank(message = "productName not empty")
  private String productName;

  @Min(value = 0)
  @NotNull(message = "price not empty")
  private int price;

//    @NotBlank(message = "imageURL not empty")
//    private String imageURl;

  private String description;
}
