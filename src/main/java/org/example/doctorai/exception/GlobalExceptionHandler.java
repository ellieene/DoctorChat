package org.example.doctorai.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EntityAlreadyExistException.class)
    public String handleException(EntityAlreadyExistException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateEmailException.class)
    public String handleException(DuplicateEmailException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JwtException.class)
    public String handleException(JwtException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.FOUND)
    @ExceptionHandler(ChatNotFoundException.class)
    public String handleException(ChatNotFoundException e) {return e.getMessage();}

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(GigaChataException.class)
    public String handleException(GigaChataException e) {return e.getMessage();}

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(TokenValidException.class)
    public String handleException(TokenValidException e) {return e.getMessage();}


    @ResponseStatus(HttpStatus.UNAUTHORIZED) // ⬅ Теперь 401 вместо 403
    @ExceptionHandler(InvalidJwtTokenException.class)
    public Map<String, String> handleInvalidJwtTokenException(InvalidJwtTokenException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "401");
        errorResponse.put("message", "Неверный токен или поврежден");
        return errorResponse;
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleAuthenticationException(AuthenticationException e) {
        return "Ошибка авторизации: " + e.getMessage();
    }

    // Если пользователь не имеет прав на доступ
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDeniedException(AccessDeniedException e) {
        return "Доступ запрещен: " + e.getMessage();
    }
}
