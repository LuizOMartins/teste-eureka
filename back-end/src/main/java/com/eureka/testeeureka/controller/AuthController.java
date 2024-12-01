package com.eureka.testeeureka.controller;


import com.eureka.testeeureka.dto.AuthResponse;
import com.eureka.testeeureka.dto.LoginRequest;
import com.eureka.testeeureka.model.Users;
import com.eureka.testeeureka.service.JwtService;
import com.eureka.testeeureka.service.impl.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final com.eureka.testeeureka.service.impl.UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Users user = userService.findByEmail(loginRequest.getEmail());
            if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.status(401).body("Email ou senha inválidos.");
            }

            String token = jwtService.generateToken(user.getEmail(), user.getRole());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao autenticar: " + e.getMessage());
        }
    }
}