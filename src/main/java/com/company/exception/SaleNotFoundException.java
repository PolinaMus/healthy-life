package com.company.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SaleNotFoundException extends RuntimeException {
    public SaleNotFoundException() {
    }

    public SaleNotFoundException(String message) {
        super(message);
    }

    public SaleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaleNotFoundException(Throwable cause) {
        super(cause);
    }

    public SaleNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
