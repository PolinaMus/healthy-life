package com.company.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ItemQtyNotEnoughException extends RuntimeException {
    public ItemQtyNotEnoughException() {
    }

    public ItemQtyNotEnoughException(String message) {
        super(message);
    }

    public ItemQtyNotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemQtyNotEnoughException(Throwable cause) {
        super(cause);
    }

    public ItemQtyNotEnoughException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
