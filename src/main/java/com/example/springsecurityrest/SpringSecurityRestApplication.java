package com.example.springsecurityrest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "API user product",
        version = "1.0.0",
        description = "This project for learning",
        contact = @Contact(
            name = "Bui Van Sy",
            email = "sybuivan1429@gmail.com"
        ),
        license = @License(
            name = "syBui",
            url = "sybuivan1429@gmail.com"
        )
    )
)
public class SpringSecurityRestApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringSecurityRestApplication.class, args);
  }

}
