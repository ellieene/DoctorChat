package org.example.doctorai.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.doctorai.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name="Аутентификация")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Регистрация")
    @PostMapping("/registration")
    public ResponseEntity<UUID> register(@RequestHeader(value = "token") String token) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(token));
    }
    @RequestMapping("/")
    public String home() {
        return "forward:/swagger-ui/index.html";
    }

    @Operation(summary = "Авторизация")
    @PostMapping("/authorization")
    public ResponseEntity<String> login(@RequestHeader(value = "token") String token) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.authorization(token));
    }

}

