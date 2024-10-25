package com.example.aisa.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final String entityName;
    private final String problem;

    public NotFoundException(String entityName, String problem) {
        super(entityName + " not found. Cause: " + problem);
        this.entityName = entityName;
        this.problem = problem;
    }
}
