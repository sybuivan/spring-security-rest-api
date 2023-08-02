package com.example.springsecurityrest.services;

import com.example.springsecurityrest.interfaces.IUserService;
import com.example.springsecurityrest.models.User;
import com.example.springsecurityrest.repositories.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceService implements IUserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public Optional<User> findByUsernameOrEmail(String userName, String email) {
    return userRepository.findByUsernameOrEmail(userName, email);
  }

  @Override
  public boolean existsByUsername(String userName) {
    return userRepository.existsByUsername(userName);
  }

  @Override
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public User createUser(User user) {
    return userRepository.save(user);
  }
}
