package com.sabakurreddit.reddit.service;

import com.sabakurreddit.reddit.config.JwtService;
import com.sabakurreddit.reddit.dto.AuthenticationRequest;
import com.sabakurreddit.reddit.dto.AuthenticationResponse;
import com.sabakurreddit.reddit.dto.RegisterRequest;
import com.sabakurreddit.reddit.exceptions.SpringRedditException;
import com.sabakurreddit.reddit.model.NotificationEmail;
import com.sabakurreddit.reddit.model.User;
import com.sabakurreddit.reddit.model.VerificationToken;
import com.sabakurreddit.reddit.repository.UserRepository;
import com.sabakurreddit.reddit.repository.VerificationTokenRepository;
import io.jsonwebtoken.Jwt;
import lombok.AllArgsConstructor;


import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {



    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final VerificationTokenRepository verificationTokenRepository;

    private final MailService mailService;
    private final JwtService jwtService;



    private final AuthenticationManager authenticationManager;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
          User user = new User();
          user.setUsername(registerRequest.getUsername());
          user.setEmail(registerRequest.getEmail());
          user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
          user.setCreated(Instant.now());
          user.setEnabled(false);
          userRepository.save(user);

         String token =  generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8041/api/auth/accountVerification/" + token));
    }

    private String generateVerificationToken(User user) {

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken= new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {

        Optional<VerificationToken> verificationToken=verificationTokenRepository.findByToken(token);
         verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));
         fatchUserAndEnable(verificationToken.get());
    }

    @Transactional
    public void fatchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User not found with name - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse authentication(AuthenticationRequest request) {


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails user= userRepository.findByUsername(request.getUsername()).orElseThrow();
        String jwttoken =jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .authenticationToken(jwttoken)
                .username(request.getUsername())
                .build();



    }
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        return ((User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
