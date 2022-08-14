package io.github.mateuszuran.restblog.exception;

public class IncorrectUserIdException extends RuntimeException {
    public IncorrectUserIdException(Long id) {
        super("User with id: " + id + " is unauthorized");
    }
}
