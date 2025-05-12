package org.example.doctorai.model.dto;

import org.example.doctorai.model.entity.User;


/**
 * Перевод с User в UserDTO
 * @param email почта User
 * @param login логин User
 */
public record UserDTO (String email, String login) {
    public UserDTO (User user) {
        this(user.getEmail(), user.getLogin());
    }
}
