package com.neonex.domain.exception;

public class RestaurantDataAccessException extends DomainException {
    public RestaurantDataAccessException(String message) {
        super(message);
    }

    public RestaurantDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
