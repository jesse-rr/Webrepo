package com.handleGod.exception;

public class BucketAlreadyContainsEndpointException extends RuntimeException {
    public BucketAlreadyContainsEndpointException(String message) {
        super(message);
    }
}
