package com.cy.store.service.ex;

public class PassWordNotFoundException extends ServiceException{
    public PassWordNotFoundException() {
        super();
    }

    public PassWordNotFoundException(String message) {
        super(message);
    }

    public PassWordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PassWordNotFoundException(Throwable cause) {
        super(cause);
    }

    protected PassWordNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
