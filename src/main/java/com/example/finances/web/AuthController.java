package com.example.finances.web;

import com.example.finances.dto.AuthDtos.*;
import com.example.finances.model.User;
import com.example.finances.security.JwtUtil;
import com.example.finances.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService users;
    private final AuthenticationManager authManager;
    private final JwtUtil jwt;

    public AuthController(UserService users, AuthenticationManager authManager, JwtUtil jwt){
        this.users = users; this.authManager = authManager; this.jwt = jwt;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req){
        User u = users.register(req.name, req.email, req.password);
        String token = jwt.generateToken(u.getEmail());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req){
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email, req.password));
        String token = jwt.generateToken(req.email);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
