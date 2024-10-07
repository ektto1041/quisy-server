package com.karpo.quisy.exceptions;

public class NotFoundWorkbookException extends CustomException {
    public NotFoundWorkbookException(String message) {
        super(message);
        this.errorCode = ErrorCode.NOT_FOUND_WORKBOOK;
    }

    public NotFoundWorkbookException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = ErrorCode.NOT_FOUND_WORKBOOK;
    }
}
