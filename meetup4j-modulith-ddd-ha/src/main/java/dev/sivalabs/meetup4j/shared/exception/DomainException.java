package dev.sivalabs.meetup4j.shared.exception;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
