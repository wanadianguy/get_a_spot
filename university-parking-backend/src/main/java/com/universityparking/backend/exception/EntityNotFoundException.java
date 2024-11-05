package com.universityparking.backend.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?> entityClass, String uniqueIdentifier) {
        super("The object " + uniqueIdentifier + " of class " + entityClass.getSimpleName() + " is not found.");
    }
}
