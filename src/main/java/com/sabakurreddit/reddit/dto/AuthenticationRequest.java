package com.sabakurreddit.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    String username;

    String password;
}
