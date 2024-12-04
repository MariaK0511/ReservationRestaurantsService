package com.reservation_restaurants_service.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String msg) {
        super(msg);
    }

    public static ResourceNotFoundException of(Class<?> entity, Object field) {
        return new ResourceNotFoundException(String.format("%s with id %s does not exist", entity.getSimpleName(), field));
    }
}
