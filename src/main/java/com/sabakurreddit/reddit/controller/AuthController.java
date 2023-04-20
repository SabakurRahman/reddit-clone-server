package com.sabakurreddit.reddit.controller;

import com.sabakurreddit.reddit.dto.AuthenticationRequest;
import com.sabakurreddit.reddit.dto.AuthenticationResponse;
import com.sabakurreddit.reddit.dto.RegisterRequest;
import com.sabakurreddit.reddit.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Successful",
                OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authService.authentication(request));
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){

        authService.verifyAccount(token);
        return new ResponseEntity<>("User Actived Successfully", OK);

    }
}
