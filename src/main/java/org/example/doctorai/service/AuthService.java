package org.example.doctorai.service;

import lombok.RequiredArgsConstructor;
import org.example.doctorai.exception.DuplicateEmailException;
import org.example.doctorai.exception.JwtException;
import org.example.doctorai.model.dto.CustomUserDetails;
import org.example.doctorai.model.entity.User;
import org.example.doctorai.model.enums.Role;
import org.example.doctorai.model.request.UserRequest;
import org.example.doctorai.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Регистрация
     *
     * @param token JWT
     * @return UUID пользователя
     */
    @Transactional
    public UUID registerUser(String token) {
        UserRequest userRequest = jwtService.getEmailAndPassword(token);
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new DuplicateEmailException("Email уже занят.");
        } else if (userRepository.existsByLogin(userRequest.getLogin())) {
            throw new DuplicateEmailException("Логин уже занят.");
        }
        User user = User.builder()
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .login(userRequest.getLogin())
                .role(Role.ROLE_USER)
                .letter(userRequest.isLetter())
                .notification(userRequest.isNotification())
                .build();

        userRepository.save(user);
        return user.getId();
    }

    /**
     * Авторизация
     *
     * @param token JWT
     * @return UUID пользователя
     */
    @Transactional
    public String authorization(String token) {
        try {
            UserRequest userRequest = jwtService.getEmailAndPassword(token);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword());
            authenticationManager.authenticate(authenticationToken);
            CustomUserDetails userDetails =
                    (CustomUserDetails) customUserDetailsService.loadUserByUsername(userRequest.getEmail());

            UsernamePasswordAuthenticationToken authenticatedToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticatedToken);

            return jwtService.generateToken(userRequest.getEmail(), userRequest.getPassword(), userRequest.getEmail());
        } catch (AuthenticationException e) {
            throw new JwtException("Не удалось авторизоваться");
        }
    }
}
