package com.company.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ItemPriceChangedException extends RuntimeException {
    public ItemPriceChangedException() {
    }

    public ItemPriceChangedException(String message) {
        super(message);
    }

    public ItemPriceChangedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemPriceChangedException(Throwable cause) {
        super(cause);
    }

    public ItemPriceChangedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
