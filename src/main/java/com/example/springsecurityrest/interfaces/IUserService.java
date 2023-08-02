package com.example.springsecurityrest.interfaces;

import com.example.springsecurityrest.models.User;
import java.util.Optional;

public interface IUserService {

  Optional<User> findByUsernameOrEmail(String email, String userName);

  boolean existsByUsername(String userName);

  boolean existsByEmail(String email);

  User createUser(User user);
}
