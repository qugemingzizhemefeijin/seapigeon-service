package com.tigerjoys.seapigeon.common.exception;

public class CrownException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CrownException(String message) {
        super(message);
    }

    public CrownException(Throwable throwable) {
        super(throwable);
    }

    public CrownException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
