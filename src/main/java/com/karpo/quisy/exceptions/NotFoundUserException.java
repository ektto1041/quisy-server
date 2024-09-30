package com.karpo.quisy.exceptions;

public class NotFoundUserException extends CustomException {
    public NotFoundUserException(String message) {
        super(message);
        this.errorCode = ErrorCode.NOT_FOUND_USER;
    }

    public NotFoundUserException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = ErrorCode.NOT_FOUND_USER;
    }
}
