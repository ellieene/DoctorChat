package org.example.doctorai.controller;

import lombok.RequiredArgsConstructor;
import org.example.doctorai.model.dto.UserDTO;
import org.example.doctorai.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Получение все {@link UserDTO} для микросервиса Letter
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get-user-in-letter")
    public ResponseEntity<List<UserDTO>> getUserInLetter() {
        return ResponseEntity.ok(userService.getUsers());
    }
}
