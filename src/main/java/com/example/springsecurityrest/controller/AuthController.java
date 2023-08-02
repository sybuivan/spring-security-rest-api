package com.example.springsecurityrest.controller;

import com.example.springsecurityrest.interfaces.IProductService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.springsecurityrest.config.JwtConfig;
import com.example.springsecurityrest.constants.Status;
import com.example.springsecurityrest.dto.RefreshTokenDto;
import com.example.springsecurityrest.exception.TokenRefreshException;
import com.example.springsecurityrest.interfaces.IRefreshToken;
import com.example.springsecurityrest.models.*;
import com.example.springsecurityrest.payload.response.JwtResponse;
import com.example.springsecurityrest.payload.response.MessageResponse;
import com.example.springsecurityrest.payload.response.ResponseObject;
import com.example.springsecurityrest.payload.response.TokenRefreshResponse;
import com.example.springsecurityrest.services.ProductServiceImp;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.springsecurityrest.dto.LoginDto;
import com.example.springsecurityrest.dto.SignUpDto;
import com.example.springsecurityrest.repositories.RoleRepository;
import com.example.springsecurityrest.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IRefreshToken refreshTokenService;

    @Autowired
    private JwtConfig jwtConfig;

//	@GetMapping("/users-webclient")
//	public Flux<UserWebClient> getUsers() {
//		return productServiceImp.getUsers();
//	}

    @PostMapping("/signin")
    public ResponseEntity<ResponseObject> authenticateUser(@RequestBody LoginDto loginDto) {
        Optional<User> user = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("User not found with userName or email", Status.FAILED));
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isMactch = passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword());

        if (isMactch) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("User signed-in successfully!.", Status.SUCCESS, user));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Password does not match stored value", Status.FAILED));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {

        // add check for username exists in a DB
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName(com.example.springsecurityrest.constants.Role.ROLE_ADMIN).get();

        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/login-jwt")
    public ResponseEntity<?> loginJwt(@Valid @RequestBody LoginDto loginDto) {
        Optional<User> user = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("User not found with userName or email", Status.FAILED));
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isMactch = passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword());

        if (isMactch) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            List<String> roleList = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            final String token = jwtConfig.generateToken(loginDto.getUsernameOrEmail(), roleList);

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.get().getId());

            Claims claims = jwtConfig.extractAllClaims(token);

            return ResponseEntity.ok(new JwtResponse(token, refreshToken.getToken(), user.get().getId(),
                    user.get().getUsername(), user.get().getEmail(), roleList));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Password does not match stored value", Status.FAILED));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUsernameOrEmail(userDetails.getUsername(), userDetails.getUsername());

        refreshTokenService.deleteByUserId(user.get().getId());
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
        String requestRefreshToken = refreshTokenDto.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    List<String> roleList = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
                    String token = jwtConfig.generateToken(user.getUsername(), roleList);
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }
}
