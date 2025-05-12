package org.example.doctorai.exception;

/**
 * Исключение, для обозначения повторения логина
 */
public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
