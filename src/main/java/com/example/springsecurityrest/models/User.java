package com.example.springsecurityrest.models;

import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}),
    @UniqueConstraint(columnNames = {"email"})})
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @NotBlank(message = "Name not empty")
  private String name;
  @NotBlank(message = "UserName not empty")
  private String username;
  @NotBlank(message = "Email not empty")
  @Email(message = "Incorrect email format")
  private String email;

  @NotBlank(message = "Password not empty")
  @Min(value = 8, message = "Password must be at least 8 characters")
  private String password;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private Set<Role> roles;

  @OneToMany(mappedBy = "user")
  private List<Order> orderList;

  @OneToOne(mappedBy = "user")
  private RefreshToken refreshToken;
}
